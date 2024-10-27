package com.example.table_service.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.table_service.dto.TableDto;
import com.example.table_service.entity.Table;
import com.example.table_service.exception.TableNotFoundException;
import com.example.table_service.repository.TableRepository;
import java.util.Collections;
import java.util.List;
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
}