package com.example.table_service.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.table_service.domain.exception.TableNotFoundException;
import com.example.table_service.domain.exception.TableWithNumberAlreadyExistsException;
import com.example.table_service.domain.model.Table;
import com.example.table_service.domain.model.TableStatus;
import com.example.table_service.domain.repository.TableRepository;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UpdateTableServiceTest {

  @Mock
  private TableRepository tableRepository;

  @InjectMocks
  private UpdateTableService underTest;

  @Test
  void execute_whenTableNumberDoesNotExist_throwsTableNotFoundException() {
    int tableNumber = 1;
    Table table = new Table(1L, "tbl1", 1, 1, TableStatus.AVAILABLE);

    TableNotFoundException thrown = assertThrows(TableNotFoundException.class, () -> {
      underTest.execute(tableNumber, table);
    });

    verify(tableRepository, times(1)).findTableByNumber(tableNumber);

    String expectedMessage = String.format("Table with number %d does not exist", tableNumber);

    assertEquals(expectedMessage, thrown.getMessage());
  }

  @Test
  void execute_whenUpdatedTableNumberDoesAlreadyExists_throwsTableWithNumberAlreadyExistsException() {
    int tableNumber = 1;
    Table table = new Table(1L, "tbl1", 1, 1, TableStatus.AVAILABLE);
    Table tableToUpdate = new Table(1L, "tbl2", 2, 1, TableStatus.AVAILABLE);

    when(tableRepository.findTableByNumber(tableNumber)).thenReturn(Optional.of(table));
    when(tableRepository.isNumberTaken(tableToUpdate.getNumber())).thenReturn(true);

    TableWithNumberAlreadyExistsException thrown = assertThrows(
        TableWithNumberAlreadyExistsException.class, () -> {
          underTest.execute(tableNumber, tableToUpdate);
        });

    verify(tableRepository, times(1)).findTableByNumber(tableNumber);
    verify(tableRepository, times(1)).isNumberTaken(tableToUpdate.getNumber());

    String expectedMessage = String.format("Table with number %d already exists",
        tableToUpdate.getNumber());

    assertEquals(expectedMessage, thrown.getMessage());
  }

  @Test
  void updateTable_whenTableNumberDoesExists_returnsUpdatedTableDto() {
    int tableNumber = 1;
    Table table = new Table(1L, "tbl1", 1, 1, TableStatus.AVAILABLE);
    Table expected = new Table(1L, "tbl2", 2, 3, TableStatus.RESERVED);

    when(tableRepository.findTableByNumber(tableNumber)).thenReturn(Optional.of(table));
    when(tableRepository.isNumberTaken(expected.getNumber())).thenReturn(false);

    Table actual = underTest.execute(tableNumber, expected);

    verify(tableRepository, times(1)).findTableByNumber(tableNumber);
    verify(tableRepository, times(1)).isNumberTaken(expected.getNumber());
    assertEquals(expected.getId(), actual.getId());
  }
}