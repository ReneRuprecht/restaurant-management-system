package com.example.table_service.service;

import com.example.table_service.dto.TableDto;
import com.example.table_service.exception.TableNotFoundException;
import com.example.table_service.exception.TableWithNumberAlreadyExistsException;
import java.util.List;


public interface TableService {

  List<TableDto> findAllTables() throws TableNotFoundException;

  TableDto create(TableDto tableToCreate) throws TableWithNumberAlreadyExistsException;

  TableDto findTableByNumber(int number) throws TableNotFoundException;

  void deleteTableByNumber(int number) throws TableNotFoundException;

  TableDto update(TableDto tableToUpdate) throws TableNotFoundException;
}
