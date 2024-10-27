package com.example.table_service.service;

import com.example.table_service.dto.TableDto;
import com.example.table_service.entity.Table;
import com.example.table_service.exception.TableNotFoundException;
import com.example.table_service.repository.TableRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
@AllArgsConstructor
public class TableServiceImpl implements TableService {

  private final TableRepository tableRepository;

  @Override
  public List<TableDto> findAllTables() throws TableNotFoundException {
    List<Table> tables = tableRepository.findAll();

    if (tables.isEmpty()) {
      throw new TableNotFoundException("Es wurden keine Tische gefunden");
    }

    return tables.stream().map(TableDto::fromEntity).collect(Collectors.toList());

  }
}
