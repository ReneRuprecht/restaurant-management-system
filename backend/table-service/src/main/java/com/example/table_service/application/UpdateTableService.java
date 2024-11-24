package com.example.table_service.application;

import com.example.table_service.application.usecase.UpdateTableUseCase;
import com.example.table_service.domain.exception.TableNotFoundException;
import com.example.table_service.domain.exception.TableWithNumberAlreadyExistsException;
import com.example.table_service.domain.model.Table;
import com.example.table_service.domain.repository.TableRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class UpdateTableService implements UpdateTableUseCase {

  private final TableRepository tableRepository;

  public UpdateTableService(TableRepository tableRepository) {
    this.tableRepository = tableRepository;
  }


  @Override
  @Transactional
  public Table execute(int tableNumber, Table updateTableData) {
    Table table = tableRepository.findTableByNumber(tableNumber).orElseThrow(
        () -> new TableNotFoundException(
            String.format("Table with number %d does not exist", tableNumber)));

    boolean isNumberTaken = tableRepository.isNumberTaken(updateTableData.getNumber());
    boolean updateNotAllowed = isNumberTaken && tableNumber != updateTableData.getNumber();

    if (updateNotAllowed) {
      throw new TableWithNumberAlreadyExistsException(
          String.format("Table with number %d already exists", updateTableData.getNumber()));
    }

    table.setName(updateTableData.getName());
    table.setNumber(updateTableData.getNumber());
    table.setSeats(updateTableData.getSeats());
    table.setTableStatus(updateTableData.getTableStatus());

    return table;
  }
}
