package com.example.table_service.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
class SaveTableServiceTest {

  @Mock
  private TableRepository tableRepository;

  @InjectMocks
  private SaveTableService underTest;


  @Test
  void execute_whenTablePassedIsNull_throwsIllegalArgumentException() {
    Table tableThatIsNull = null;

    String expectedMessage = "Table must not be null";

    IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
      underTest.execute(tableThatIsNull);
    });

    verify(tableRepository, times(0)).save(any());
    verify(tableRepository, times(0)).findTableByNumber(anyInt());
    assertEquals(expectedMessage, thrown.getMessage());
  }

  @Test
  void execute_whenTableNumberAlreadyExists_throwsTableWithNumberAlreadyExistsException() {
    Table tableToSave = new Table(null, "tbl1", 1, 1);
    Table tableThatIsAlreadyPresent = new Table(1L, "tbl1", 1, 1, TableStatus.AVAILABLE);

    when(tableRepository.findTableByNumber(tableToSave.getNumber())).thenReturn(
        Optional.of(tableThatIsAlreadyPresent));

    String expectedMessage = String.format("Table with number %d already exists",
        tableToSave.getNumber());

    TableWithNumberAlreadyExistsException thrown = assertThrows(
        TableWithNumberAlreadyExistsException.class, () -> {
          underTest.execute(tableToSave);
        });

    verify(tableRepository, times(0)).save(any());
    verify(tableRepository, times(1)).findTableByNumber(tableToSave.getNumber());
    assertEquals(expectedMessage, thrown.getMessage());
  }

  @Test
  void execute_whenTableNumberDoesNotExists_returnSavedTable() {
    Table tableToSave = new Table(null, "tbl1", 1, 1);
    Long savedTableId = 1L;

    Table expected = new Table(1L, "tbl1", 1, 1, TableStatus.AVAILABLE);

    when(tableRepository.findTableByNumber(tableToSave.getNumber())).thenReturn(Optional.empty());

    when(tableRepository.save(tableToSave)).thenReturn(savedTableId);

    Table actual = underTest.execute(tableToSave);

    verify(tableRepository, times(1)).save(any());
    verify(tableRepository, times(1)).findTableByNumber(tableToSave.getNumber());
    assertEquals(expected.getId(), actual.getId());
    assertEquals(expected.getName(), actual.getName());
    assertEquals(expected.getNumber(), actual.getNumber());
    assertEquals(expected.getSeats(), actual.getSeats());
    assertEquals(expected.getTableStatus(), actual.getTableStatus());
  }
}