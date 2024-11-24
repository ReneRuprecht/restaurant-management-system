package com.example.table_service.infrastructure.adapter.out.jpa;

import com.example.table_service.domain.model.Table;
import com.example.table_service.infrastructure.adapter.out.jpa.entity.TableEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TableEntityRepository extends JpaRepository<TableEntity, Long> {

  Optional<Table> findTableByNumber(int number);

  boolean isNumberTaken(int number);

}
