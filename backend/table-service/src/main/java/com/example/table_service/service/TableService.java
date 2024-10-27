package com.example.table_service.service;

import com.example.table_service.dto.TableDto;
import com.example.table_service.exception.TableNotFoundException;
import java.util.List;


public interface TableService {

  List<TableDto> findAllTables() throws TableNotFoundException;

  TableDto create(TableDto tableToCreate);
}
