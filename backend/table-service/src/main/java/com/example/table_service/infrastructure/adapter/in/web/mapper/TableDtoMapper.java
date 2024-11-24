package com.example.table_service.infrastructure.adapter.in.web.mapper;

import com.example.table_service.domain.model.Table;
import com.example.table_service.domain.model.TableStatus;
import com.example.table_service.infrastructure.adapter.in.web.dto.request.TableCreateRequestDto;
import com.example.table_service.infrastructure.adapter.in.web.dto.request.TableUpdateRequestDto;
import com.example.table_service.infrastructure.adapter.in.web.dto.response.TableCreatedResponseDto;
import com.example.table_service.infrastructure.adapter.in.web.dto.response.TableResponseDto;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class TableDtoMapper {

  public TableCreatedResponseDto fromTableToTableCreatedResponseDto(Table table) {
    return new TableCreatedResponseDto(table.getId(), table.getName(), table.getNumber(),
        table.getSeats(), table.getTableStatus());

  }

  public List<TableResponseDto> fromTablesToTableResponseDtos(List<Table> tables) {
    return tables.stream().map(
        t -> new TableResponseDto(t.getId(), t.getName(), t.getNumber(), t.getSeats(),
            t.getTableStatus())).toList();

  }

  public TableResponseDto fromTableToTableResponseDto(Table table) {
    return new TableResponseDto(table.getId(), table.getName(), table.getNumber(), table.getSeats(),
        table.getTableStatus());

  }

  public Table fromTableCreateRequestToTable(TableCreateRequestDto tableCreateRequestDto) {

    return new Table(null, tableCreateRequestDto.getName(), tableCreateRequestDto.getNumber(),
        tableCreateRequestDto.getSeats(), TableStatus.AVAILABLE);
  }

  public Table fromTableUpdateRequestToTable(TableUpdateRequestDto tableUpdateRequestDto) {

    return new Table(null, tableUpdateRequestDto.name(), tableUpdateRequestDto.number(),
        tableUpdateRequestDto.seats(), tableUpdateRequestDto.tableStatus());
  }


}
