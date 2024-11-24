package com.example.table_service.application.usecase;

import com.example.table_service.domain.model.Table;

public interface UpdateTableUseCase {

  Table execute(int tableNumber, Table updateTableData);
}
