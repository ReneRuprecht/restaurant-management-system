package com.example.table_service.infrastructure.adapter.out.jpa;

import com.example.table_service.domain.model.Table;
import com.example.table_service.domain.repository.TableRepository;
import com.example.table_service.infrastructure.adapter.out.jpa.entity.TableEntity;
import com.example.table_service.infrastructure.adapter.out.jpa.mapper.TableEntityMapper;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@AllArgsConstructor
@Repository
public class JpaTableRepositoryImpl implements TableRepository {

  private final TableEntityRepository tableEntityRepository;
  private final TableEntityMapper tableEntityMapper;

  @Override
  public Optional<Table> findTableByNumber(int number) {
    return tableEntityRepository.findTableByNumber(number);
  }

  @Override
  public List<Table> findAll() {
    return tableEntityRepository.findAll().stream().map(tableEntityMapper::fromTableEntity)
        .toList();
  }

  @Override
  public Long save(Table table) {
    TableEntity entity = tableEntityMapper.fromTable(table);
    return tableEntityRepository.save(entity).getId();
  }

  @Override
  public void delete(Table table) {
    TableEntity entity = tableEntityMapper.fromTable(table);
    tableEntityRepository.delete(entity);

  }

  @Override
  public boolean isNumberTaken(int number) {
    return tableEntityRepository.isNumberTaken(number);
  }
}
