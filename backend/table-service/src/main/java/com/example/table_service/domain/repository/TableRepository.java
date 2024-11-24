package com.example.table_service.domain.repository;

import com.example.table_service.domain.model.Table;
import java.util.List;
import java.util.Optional;

public interface TableRepository {

  Optional<Table> findTableByNumber(int number);

  List<Table> findAll();

  Long save(Table table);

  void delete(Table table);

  boolean isNumberTaken(int number);
}
