package com.example.table_service.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.table_service.dto.TableDto;
import com.example.table_service.entity.Table;
import com.example.table_service.exception.TableNotFoundException;
import com.example.table_service.exception.TableWithNumberAlreadyExistsException;
import com.example.table_service.model.TableStatus;
import com.example.table_service.repository.TableRepository;
import com.example.table_service.util.TableMapper;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TableServiceImplTest {

  @Mock
  private TableRepository tableRepository;

  @Mock
  private TableMapper tableMapper;

  @InjectMocks
  private TableServiceImpl underTest;

  @Test
  void findAllTables_whenTablesDoNotExist_throwsTablesNotFoundException() {

    String expectedMessage = "Es wurden keine Tische gefunden";

    when(tableRepository.findAll()).thenReturn(Collections.emptyList());

    TableNotFoundException thrown = assertThrows(TableNotFoundException.class, () -> {
      underTest.findAllTables();
    });

    verify(tableRepository, times(1)).findAll();
    assertEquals(expectedMessage, thrown.getMessage());
  }

  @Test
  void findAllTables_whenTablesExist_returnListOfTableDto() {

    TableDto tblDto1 = TableDto.builder().name("tbl1").number(1).seats(1)
        .tableStatus(TableStatus.RESERVED).build();
    TableDto tblDto2 = TableDto.builder().name("tbl2").number(2).seats(2)
        .tableStatus(TableStatus.RESERVED).build();
    List<TableDto> expected = List.of(tblDto1, tblDto2);

    Table tbl1 = Table.builder().id(1L).name("tbl1").number(1).seats(1)
        .tableStatus(TableStatus.RESERVED).build();
    Table tbl2 = Table.builder().id(2L).name("tbl2").number(2).seats(2)
        .tableStatus(TableStatus.RESERVED).build();

    when(tableMapper.fromTableToTableDto(tbl1)).thenReturn(tblDto1);
    when(tableMapper.fromTableToTableDto(tbl2)).thenReturn(tblDto2);

    when(tableRepository.findAll()).thenReturn(List.of(tbl1, tbl2));

    List<TableDto> actual = underTest.findAllTables();

    verify(tableRepository, times(1)).findAll();
    assertEquals(expected, actual);
    assertEquals(expected.size(), actual.size());
    assertEquals(expected.getFirst(), actual.getFirst());
    assertEquals(expected.get(1), actual.get(1));
  }

  @Test
  void create_whenTableNumberAlreadyExists_throwsTableWithNumberAlreadyExistsException() {
    TableDto tblDto1 = TableDto.builder().name("tbl1").number(1).seats(1)
        .tableStatus(TableStatus.RESERVED).build();
    Table table = Table.builder().name("tbl1").number(1).seats(1).tableStatus(TableStatus.RESERVED)
        .build();

    when(tableRepository.findTableByNumber(tblDto1.getNumber())).thenReturn(
        Optional.ofNullable(table));

    String expectedMessage = String.format("Der Tisch mit der Nummer %d existiert bereits",
        tblDto1.getNumber());

    TableWithNumberAlreadyExistsException thrown = assertThrows(
        TableWithNumberAlreadyExistsException.class, () -> {
          underTest.create(tblDto1);
        });

    assert table != null;
    verify(tableRepository, times(0)).save(table);
    verify(tableRepository, times(1)).findTableByNumber(tblDto1.getNumber());
    assertEquals(expectedMessage, thrown.getMessage());

  }

  @Test
  void create_whenTableNumberDoesNotExists_returnTableDto() {
    TableDto tblDto1 = TableDto.builder().name("tbl1").number(1).seats(1)
        .tableStatus(TableStatus.AVAILABLE).build();
    Table table = Table.builder().name("tbl1").number(1).seats(1).tableStatus(TableStatus.AVAILABLE)
        .build();

    when(tableMapper.fromTableDtoToEntity(tblDto1)).thenReturn(table);

    when(tableRepository.findTableByNumber(tblDto1.getNumber())).thenReturn(Optional.empty());

    TableDto actual = underTest.create(tblDto1);

    verify(tableRepository, times(1)).save(any(Table.class));
    verify(tableRepository, times(1)).findTableByNumber(tblDto1.getNumber());
    assertEquals(tblDto1, actual);
  }

  @Test
  void findTableByNumber_whenTableNumberDoesNotExists_throwsTableNotFoundException() {
    int number = 1;

    when(tableRepository.findTableByNumber(number)).thenReturn(Optional.empty());

    String expectedMessage = String.format(
        "Der Tisch mit der Nummer %d konnte nicht gefunden werden", number);

    TableNotFoundException thrown = assertThrows(TableNotFoundException.class, () -> {
      underTest.findTableByNumber(number);
    });

    verify(tableRepository, times(1)).findTableByNumber(number);
    assertEquals(expectedMessage, thrown.getMessage());
  }

  @Test
  void findTableByNumber_whenTableNumberDoesExists_returnsTableDto() {
    TableDto expected = TableDto.builder().name("tbl1").number(1).seats(1)
        .tableStatus(TableStatus.AVAILABLE).build();
    Table table = Table.builder().name("tbl1").number(1).seats(1).tableStatus(TableStatus.AVAILABLE)
        .build();

    when(tableMapper.fromTableToTableDto(table)).thenReturn(expected);

    when(tableRepository.findTableByNumber(table.getNumber())).thenReturn(Optional.of(table));

    TableDto actual = underTest.findTableByNumber(table.getNumber());

    verify(tableRepository, times(1)).findTableByNumber(table.getNumber());
    assertEquals(expected, actual);
  }

  @Test
  void deleteTableByNumber_whenTableNumberDoesNotExists_throwsTableNotFoundException() {
    int number = 1;

    when(tableRepository.findTableByNumber(number)).thenReturn(Optional.empty());

    String expectedMessage = String.format(
        "Der Tisch mit der Nummer %d konnte nicht gefunden werden", number);

    TableNotFoundException thrown = assertThrows(TableNotFoundException.class, () -> {
      underTest.deleteTableByNumber(number);
    });

    verify(tableRepository, times(1)).findTableByNumber(number);
    assertEquals(expectedMessage, thrown.getMessage());
  }

  @Test
  void deleteTableByNumber_whenTableNumberDoesExists_isSuccessful() {
    Table table = Table.builder().name("tbl1").number(1).seats(1).tableStatus(TableStatus.AVAILABLE)
        .build();

    when(tableRepository.findTableByNumber(table.getNumber())).thenReturn(Optional.of(table));

    underTest.deleteTableByNumber(table.getNumber());

    verify(tableRepository, times(1)).findTableByNumber(table.getNumber());
    verify(tableRepository, times(1)).delete(table);
  }

  @Test
  void updateTable_whenTableNumberDoesNotExists_throwsTableNotFoundException() {
    TableDto tableDto = TableDto.builder().name("tbl1").number(1).seats(1)
        .tableStatus(TableStatus.AVAILABLE).build();

    TableNotFoundException thrown = assertThrows(TableNotFoundException.class, () -> {
      underTest.update(tableDto);
    });

    verify(tableRepository, times(1)).findTableByNumber(tableDto.getNumber());
    String expectedMessage = String.format(
        "Der Tisch mit der Nummer %d konnte nicht gefunden werden", tableDto.getNumber());

    assertEquals(expectedMessage, thrown.getMessage());
  }

  @Test
  void updateTable_whenTableNumberDoesExists_returnsUpdatedTableDto() {
    TableDto expectedTableDto = TableDto.builder().name("tbl1_updated").number(1).seats(3)
        .tableStatus(TableStatus.AVAILABLE).build();
    Table table = Table.builder().name("tbl1").number(1).seats(1).tableStatus(TableStatus.AVAILABLE)
        .build();

    when(tableMapper.fromTableToTableDto(table)).thenReturn(expectedTableDto);

    when(tableRepository.findTableByNumber(table.getNumber())).thenReturn(Optional.of(table));

    TableDto actual = underTest.update(expectedTableDto);

    verify(tableRepository, times(1)).findTableByNumber(expectedTableDto.getNumber());
    assertEquals(expectedTableDto, actual);

  }
}