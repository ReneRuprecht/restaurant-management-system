package com.example.table_service.util;

import com.example.table_service.dto.TableDto;
import com.example.table_service.dto.request.TableCreateRequest;
import com.example.table_service.dto.request.TableUpdateRequest;
import com.example.table_service.entity.Table;
import com.example.table_service.model.TableStatus;
import org.springframework.stereotype.Component;

@Component
public class TableMapper {

  public Table fromTableDtoToEntity(TableDto tableDto) {

    return Table.builder().name(tableDto.getName()).number(tableDto.getNumber())
        .seats(tableDto.getSeats()).tableStatus(tableDto.getTableStatus()).build();
  }

  public TableDto fromTableToTableDto(Table table) {

    return new TableDto(table.getName(), table.getNumber(), table.getSeats(),
        table.getTableStatus());
  }

  public TableDto fromTableCreateRequestToTableDto(TableCreateRequest tableCreateRequest) {

    return new TableDto(tableCreateRequest.getName(), tableCreateRequest.getNumber(),
        tableCreateRequest.getSeats(), TableStatus.AVAILABLE);
  }

  public TableDto fromTableUpdateRequestToTableDto(TableUpdateRequest tableUpdateRequest) {

    return new TableDto(tableUpdateRequest.name(), tableUpdateRequest.number(),
        tableUpdateRequest.seats(), tableUpdateRequest.tableStatus());
  }

}
