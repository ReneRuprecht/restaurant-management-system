package com.example.table_service.application;

import com.example.table_service.application.usecase.SaveTableUseCase;
import com.example.table_service.domain.exception.TableWithNumberAlreadyExistsException;
import com.example.table_service.domain.model.Table;
import com.example.table_service.domain.repository.TableRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class SaveTableService implements SaveTableUseCase {

  private final TableRepository tableRepository;

  public SaveTableService(TableRepository tableRepository) {
    this.tableRepository = tableRepository;
  }

  public Table execute(Table table) {
    Assert.notNull(table, "Table must not be null");
    boolean isPresent = tableRepository.findTableByNumber(table.getNumber()).isPresent();

    if (isPresent) {
      throw new TableWithNumberAlreadyExistsException(
          String.format("Table with number %d already exists", table.getNumber()));
    }

    Long savedTableId = tableRepository.save(table);
    return new Table(savedTableId, table.getName(), table.getNumber(), table.getSeats(),
        table.getTableStatus());
  }

}
