package com.example.table_service.application;

import com.example.table_service.application.usecase.FindTableByNumberUseCase;
import com.example.table_service.domain.exception.TableNotFoundException;
import com.example.table_service.domain.model.Table;
import com.example.table_service.domain.repository.TableRepository;
import org.springframework.stereotype.Service;

@Service
public class FindTableByNumberService implements FindTableByNumberUseCase {

  private final TableRepository tableRepository;

  public FindTableByNumberService(TableRepository tableRepository) {
    this.tableRepository = tableRepository;
  }

  @Override
  public Table execute(int number) {
    return tableRepository.findTableByNumber(number).orElseThrow(() -> new TableNotFoundException(
        String.format("Table with number %d does not exists", number)));
  }

}
