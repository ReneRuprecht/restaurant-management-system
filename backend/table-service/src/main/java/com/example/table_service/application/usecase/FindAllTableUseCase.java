package com.example.table_service.application.usecase;

import com.example.table_service.domain.model.Table;
import java.util.List;

public interface FindAllTableUseCase {

  List<Table> execute();
}
