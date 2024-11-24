package com.example.table_service.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.table_service.domain.exception.TableNotFoundException;
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
class DeleteTableServiceTest {

  @Mock
  private TableRepository tableRepository;

  @InjectMocks
  private DeleteTableService underTest;


  @Test
  void execute_whenTableNumberDoesExists_isSuccessful() {
    Table table = new Table(1L, "tbl1", 1, 1, TableStatus.AVAILABLE);

    when(tableRepository.findTableByNumber(table.getNumber())).thenReturn(Optional.of(table));

    underTest.execute(table.getNumber());

    verify(tableRepository, times(1)).findTableByNumber(table.getNumber());
    verify(tableRepository, times(1)).delete(table);
  }

  @Test
  void execute_whenTableNumberDoesNotExists_throwsTableNotFoundException() {
    Table table = new Table(1L, "tbl1", 1, 1, TableStatus.AVAILABLE);
    String expectedMessage = String.format("Table with number %d does not exists",
        table.getNumber());

    when(tableRepository.findTableByNumber(table.getNumber())).thenReturn(Optional.empty());

    TableNotFoundException thrown = assertThrows(TableNotFoundException.class, () -> {
      underTest.execute(table.getNumber());
    });

    verify(tableRepository, times(1)).findTableByNumber(table.getNumber());
    verify(tableRepository, times(0)).delete(table);

    assertEquals(expectedMessage, thrown.getMessage());
  }
}