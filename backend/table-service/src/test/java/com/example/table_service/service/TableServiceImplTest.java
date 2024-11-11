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
import com.example.table_service.exception.TableWithDisplayNumberAlreadyExistsException;
import com.example.table_service.repository.TableRepository;
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

    TableDto tblDto1 = TableDto.builder().name("tbl1").displayNumber(1).seats(1).build();
    TableDto tblDto2 = TableDto.builder().name("tbl2").displayNumber(2).seats(2).build();
    List<TableDto> expected = List.of(tblDto1, tblDto2);

    Table tbl1 = Table.builder().id(1L).name("tbl1").displayNumber(1).seats(1).build();
    Table tbl2 = Table.builder().id(2L).name("tbl2").displayNumber(2).seats(2).build();

    when(tableRepository.findAll()).thenReturn(List.of(tbl1, tbl2));

    List<TableDto> actual = underTest.findAllTables();

    verify(tableRepository, times(1)).findAll();
    assertEquals(expected, actual);
    assertEquals(expected.size(), actual.size());
    assertEquals(expected.getFirst(), actual.getFirst());
    assertEquals(expected.get(1), actual.get(1));
  }

  @Test
  void create_whenTableDisplayNumberAlreadyExists_throwsTableWithDisplayNumberAlreadyExistsException() {
    TableDto tblDto1 = TableDto.builder().name("tbl1").displayNumber(1).seats(1).build();
    Table table = Table.builder().name("tbl1").displayNumber(1).seats(1).build();

    when(tableRepository.findTableByDisplayNumber(tblDto1.displayNumber())).thenReturn(
        Optional.ofNullable(table));

    String expectedMessage = String.format("Der Tisch mit der Nummer %d existiert bereits",
        tblDto1.displayNumber());

    TableWithDisplayNumberAlreadyExistsException thrown = assertThrows(
        TableWithDisplayNumberAlreadyExistsException.class, () -> {
          underTest.create(tblDto1);
        });

    assert table != null;
    verify(tableRepository, times(0)).save(table);
    verify(tableRepository, times(1)).findTableByDisplayNumber(tblDto1.displayNumber());
    assertEquals(expectedMessage, thrown.getMessage());

  }

  @Test
  void create_whenTableDisplayNumberDoesNotExists_returnTableDto() {
    TableDto tblDto1 = TableDto.builder().name("tbl1").displayNumber(1).seats(1).build();

    when(tableRepository.findTableByDisplayNumber(tblDto1.displayNumber())).thenReturn(
        Optional.empty());

    TableDto actual = underTest.create(tblDto1);

    verify(tableRepository, times(1)).save(any(Table.class));
    verify(tableRepository, times(1)).findTableByDisplayNumber(tblDto1.displayNumber());
    assertEquals(tblDto1, actual);
  }

  @Test
  void findTableByDisplayNumber_whenTableDisplayNumberDoesNotExists_throwsTableNotFoundException() {
    int displayNumber = 1;

    when(tableRepository.findTableByDisplayNumber(displayNumber)).thenReturn(Optional.empty());

    String expectedMessage = String.format(
        "Der Tisch mit der Nummer %d konnte nicht gefunden werden", displayNumber);

    TableNotFoundException thrown = assertThrows(TableNotFoundException.class, () -> {
      underTest.findTableByDisplayNumber(displayNumber);
    });

    verify(tableRepository, times(1)).findTableByDisplayNumber(displayNumber);
    assertEquals(expectedMessage, thrown.getMessage());
  }

  @Test
  void findTableByDisplayNumber_whenTableDisplayNumberDoesExists_returnsTableDto() {
    TableDto expected = TableDto.builder().name("tbl1").displayNumber(1).seats(1).build();
    Table table = Table.builder().name("tbl1").displayNumber(1).seats(1).build();

    when(tableRepository.findTableByDisplayNumber(table.getDisplayNumber())).thenReturn(
        Optional.of(table));

    TableDto actual = underTest.findTableByDisplayNumber(table.getDisplayNumber());

    verify(tableRepository, times(1)).findTableByDisplayNumber(table.getDisplayNumber());
    assertEquals(expected, actual);
  }

  @Test
  void deleteTableByDisplayNumber_whenTableDisplayNumberDoesNotExists_throwsTableNotFoundException() {
    int displayNumber = 1;

    when(tableRepository.findTableByDisplayNumber(displayNumber)).thenReturn(Optional.empty());

    String expectedMessage = String.format(
        "Der Tisch mit der Nummer %d konnte nicht gefunden werden", displayNumber);

    TableNotFoundException thrown = assertThrows(TableNotFoundException.class, () -> {
      underTest.findTableByDisplayNumber(displayNumber);
    });

    verify(tableRepository, times(1)).findTableByDisplayNumber(displayNumber);
    assertEquals(expectedMessage, thrown.getMessage());
  }

  @Test
  void deleteTableByDisplayNumber_whenTableDisplayNumberDoesExists_isSuccessful() {
    Table table = Table.builder().name("tbl1").displayNumber(1).seats(1).build();

    when(tableRepository.findTableByDisplayNumber(table.getDisplayNumber())).thenReturn(
        Optional.of(table));

    underTest.deleteTableByDisplayNumber(table.getDisplayNumber());

    verify(tableRepository, times(1)).findTableByDisplayNumber(table.getDisplayNumber());
    verify(tableRepository, times(1)).delete(table);
  }
}