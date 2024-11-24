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
class FindTableByNumberServiceTest {

  @Mock
  private TableRepository tableRepository;

  @InjectMocks
  private FindTableByNumberService underTest;

  @Test
  void execute_whenTableNumberDoesNotExists_throwsTableNotFoundException() {
    int number = 1;

    when(tableRepository.findTableByNumber(number)).thenReturn(Optional.empty());

    String expectedMessage = String.format(
        "Table with number %d does not exists", number);

    TableNotFoundException thrown = assertThrows(TableNotFoundException.class, () -> {
      underTest.execute(number);
    });

    verify(tableRepository, times(1)).findTableByNumber(number);
    assertEquals(expectedMessage, thrown.getMessage());
  }

  @Test
  void execute_whenTableNumberDoesExists_returnsTable() {
    Table expected = new Table(1L, "tbl1", 1, 1, TableStatus.AVAILABLE);
    int tableNumberToSearch = 1;

    when(tableRepository.findTableByNumber(tableNumberToSearch)).thenReturn(Optional.of(expected));

    Table actual = underTest.execute(tableNumberToSearch);

    verify(tableRepository, times(1)).findTableByNumber(tableNumberToSearch);
    assertEquals(expected, actual);
  }
}