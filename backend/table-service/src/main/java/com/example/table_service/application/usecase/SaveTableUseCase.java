package com.example.table_service.application.usecase;

import com.example.table_service.domain.model.Table;

public interface SaveTableUseCase {

  Table execute(Table table);

}
