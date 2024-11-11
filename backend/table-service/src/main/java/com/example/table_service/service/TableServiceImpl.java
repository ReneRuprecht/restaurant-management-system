package com.example.table_service.service;

import com.example.table_service.dto.TableDto;
import com.example.table_service.entity.Table;
import com.example.table_service.exception.TableNotFoundException;
import com.example.table_service.exception.TableWithDisplayNumberAlreadyExistsException;
import com.example.table_service.repository.TableRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
@AllArgsConstructor
public class TableServiceImpl implements TableService {

  private final TableRepository tableRepository;

  @Override
  public List<TableDto> findAllTables() throws TableNotFoundException {
    List<Table> tables = tableRepository.findAll();

    if (tables.isEmpty()) {
      throw new TableNotFoundException("Es wurden keine Tische gefunden");
    }

    return tables.stream().map(TableDto::fromEntity).collect(Collectors.toList());

  }

  @Override
  public TableDto create(TableDto tableToCreate)
      throws TableWithDisplayNumberAlreadyExistsException {
    if (tableRepository.findTableByDisplayNumber(tableToCreate.displayNumber()).isPresent()) {

      throw new TableWithDisplayNumberAlreadyExistsException(
          String.format("Der Tisch mit der Nummer %d existiert bereits",
              tableToCreate.displayNumber()));
    }

    tableRepository.save(TableDto.toEntity(tableToCreate));

    return tableToCreate;
  }

  @Override
  public TableDto findTableByDisplayNumber(int displayNumber) throws TableNotFoundException {
    Table table = tableRepository.findTableByDisplayNumber(displayNumber).orElseThrow(
        () -> new TableNotFoundException(
            String.format("Der Tisch mit der Nummer %d konnte nicht gefunden werden",
                displayNumber)));
    return TableDto.fromEntity(table);
  }

  @Override
  public void deleteTableByDisplayNumber(int displayNumber) throws TableNotFoundException {
    Table table = tableRepository.findTableByDisplayNumber(displayNumber).orElseThrow(
        () -> new TableNotFoundException(
            String.format("Der Tisch mit der Nummer %d konnte nicht gefunden werden",
                displayNumber)));

    tableRepository.delete(table);

  }

}
