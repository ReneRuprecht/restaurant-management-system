package com.example.table_service.application;

import com.example.table_service.application.usecase.DeleteTableByNumberUseCase;
import com.example.table_service.domain.exception.TableNotFoundException;
import com.example.table_service.domain.model.Table;
import com.example.table_service.domain.repository.TableRepository;
import org.springframework.stereotype.Service;

@Service
public class DeleteTableService implements DeleteTableByNumberUseCase {

  private final TableRepository tableRepository;

  public DeleteTableService(TableRepository tableRepository) {
    this.tableRepository = tableRepository;
  }

  @Override
  public void execute(int number) {
    Table table = tableRepository.findTableByNumber(number).orElseThrow(
        () -> new TableNotFoundException(
            String.format("Table with number %d does not exists", number)));

    tableRepository.delete(table);
  }
}
