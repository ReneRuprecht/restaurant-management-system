package com.example.table_service.application;

import com.example.table_service.application.usecase.FindAllTableUseCase;
import com.example.table_service.domain.model.Table;
import com.example.table_service.domain.repository.TableRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class FindAllTableService implements FindAllTableUseCase {

  private final TableRepository tableRepository;

  public FindAllTableService(TableRepository tableRepository) {
    this.tableRepository = tableRepository;
  }

  @Override
  public List<Table> execute() {
    return tableRepository.findAll();
  }
}
