package com.example.table_service.infrastructure.adapter.out.jpa.mapper;

import com.example.table_service.domain.model.Table;
import com.example.table_service.infrastructure.adapter.out.jpa.entity.TableEntity;
import org.springframework.stereotype.Component;

@Component
public class TableEntityMapper {

  public TableEntity fromTable(Table table) {
    return TableEntity.builder().name(table.getName()).number(table.getNumber())
        .seats(table.getSeats()).tableStatus(table.getTableStatus()).build();
  }

  public Table fromTableEntity(TableEntity entity) {
    return new Table(entity.getId(), entity.getName(), entity.getNumber(), entity.getSeats(),
        entity.getTableStatus());
  }


}
