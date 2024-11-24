package com.example.table_service.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.table_service.domain.model.Table;
import com.example.table_service.domain.model.TableStatus;
import com.example.table_service.domain.repository.TableRepository;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class FindAllTableServiceTest {

  @Mock
  private TableRepository tableRepository;

  @InjectMocks
  private FindAllTableService underTest;

  @Test
  void execute_whenTablesDoNotExist_throwsTablesNotFoundException() {

    List<Table> expected = Collections.emptyList();
    when(tableRepository.findAll()).thenReturn(Collections.emptyList());

    List<Table> actual = underTest.execute();

    verify(tableRepository, times(1)).findAll();
    assertEquals(expected, actual);
  }

  @Test
  void findAllTables_whenTablesExist_returnListOfTableDto() {

    Table tableOne = new Table(1L, "tbl1", 1, 1, TableStatus.AVAILABLE);
    Table tableTwo = new Table(2L, "tbl2", 2, 1, TableStatus.AVAILABLE);
    Table tableThree = new Table(3L, "tbl3", 3, 1, TableStatus.RESERVED);

    List<Table> expected = List.of(tableOne, tableTwo, tableThree);

    when(tableRepository.findAll()).thenReturn(expected);

    List<Table> actual = underTest.execute();

    verify(tableRepository, times(1)).findAll();
    assertEquals(expected, actual);
    assertEquals(expected.size(), actual.size());
    assertEquals(expected.getFirst(), actual.getFirst());
    assertEquals(expected.get(1), actual.get(1));
  }
}