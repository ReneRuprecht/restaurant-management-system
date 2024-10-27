package com.example.table_service.repository;

import com.example.table_service.entity.Table;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TableRepository extends JpaRepository<Table, Long> {

  Optional<Table> findTableByDisplayNumber(int displayNumber);

}
