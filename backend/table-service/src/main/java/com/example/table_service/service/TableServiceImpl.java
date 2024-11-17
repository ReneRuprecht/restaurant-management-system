package com.example.table_service.service;

import com.example.table_service.dto.TableDto;
import com.example.table_service.entity.Table;
import com.example.table_service.exception.TableNotFoundException;
import com.example.table_service.exception.TableWithNumberAlreadyExistsException;
import com.example.table_service.repository.TableRepository;
import com.example.table_service.util.TableMapper;
import jakarta.transaction.Transactional;
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

  private final TableMapper tableMapper;

  @Override
  public List<TableDto> findAllTables() throws TableNotFoundException {
    List<Table> tables = tableRepository.findAll();

    if (tables.isEmpty()) {
      throw new TableNotFoundException("Es wurden keine Tische gefunden");
    }

    return tables.stream().map(tableMapper::fromTableToTableDto).collect(Collectors.toList());

  }

  @Override
  @Transactional
  public TableDto create(TableDto tableToCreate) throws TableWithNumberAlreadyExistsException {
    if (tableRepository.findTableByNumber(tableToCreate.getNumber()).isPresent()) {

      throw new TableWithNumberAlreadyExistsException(
          String.format("Der Tisch mit der Nummer %d existiert bereits",
              tableToCreate.getNumber()));
    }

    Table table = tableMapper.fromTableDtoToEntity(tableToCreate);

    tableRepository.save(table);

    return tableToCreate;
  }

  @Override
  public TableDto findTableByNumber(int number) throws TableNotFoundException {
    Table table = tableRepository.findTableByNumber(number).orElseThrow(
        () -> new TableNotFoundException(
            String.format("Der Tisch mit der Nummer %d konnte nicht gefunden werden", number)));
    return tableMapper.fromTableToTableDto(table);
  }

  @Override
  @Transactional
  public void deleteTableByNumber(int number) throws TableNotFoundException {
    Table table = tableRepository.findTableByNumber(number).orElseThrow(
        () -> new TableNotFoundException(
            String.format("Der Tisch mit der Nummer %d konnte nicht gefunden werden", number)));

    tableRepository.delete(table);

  }

  @Override
  @Transactional
  public TableDto update(TableDto tableToUpdate) {
    Table table = tableRepository.findTableByNumber(tableToUpdate.getNumber()).orElseThrow(
        () -> new TableNotFoundException(
            String.format("Der Tisch mit der Nummer %d konnte nicht gefunden werden",
                tableToUpdate.getNumber())));

    table.setName(tableToUpdate.getName());
    table.setSeats(tableToUpdate.getSeats());
    table.setTableStatus(tableToUpdate.getTableStatus());

    return tableMapper.fromTableToTableDto(table);
  }

}
