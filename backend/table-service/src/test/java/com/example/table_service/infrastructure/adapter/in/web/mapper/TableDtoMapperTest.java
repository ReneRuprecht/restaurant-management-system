package com.example.table_service.infrastructure.adapter.in.web.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.example.table_service.domain.model.Table;
import com.example.table_service.domain.model.TableStatus;
import com.example.table_service.infrastructure.adapter.in.web.dto.request.TableCreateRequestDto;
import com.example.table_service.infrastructure.adapter.in.web.dto.request.TableUpdateRequestDto;
import com.example.table_service.infrastructure.adapter.in.web.dto.response.TableCreatedResponseDto;
import com.example.table_service.infrastructure.adapter.in.web.dto.response.TableResponseDto;
import java.util.List;
import org.junit.jupiter.api.Test;

class TableDtoMapperTest {

  TableDtoMapper underTest = new TableDtoMapper();

  @Test
  void fromTableToTableCreatedResponseDto() {
    Table table = new Table(1L, "tbl1", 1, 1, TableStatus.AVAILABLE);
    TableCreatedResponseDto expected = new TableCreatedResponseDto(table.getId(), table.getName(),
        table.getNumber(), table.getSeats(), table.getTableStatus());

    TableCreatedResponseDto actual = underTest.fromTableToTableCreatedResponseDto(table);

    assertEquals(expected, actual);
  }

  @Test
  void fromTablesToTableResponseDtos() {
    Table tableOne = new Table(1L, "tbl1", 1, 1, TableStatus.AVAILABLE);
    Table tableTwo = new Table(1L, "tbl1", 1, 1, TableStatus.AVAILABLE);
    List<Table> tables = List.of(tableOne, tableTwo);

    List<TableResponseDto> expected = tables.stream().map(
        t -> new TableResponseDto(t.getId(), t.getName(), t.getNumber(), t.getSeats(),
            t.getTableStatus())).toList();

    List<TableResponseDto> actual = underTest.fromTablesToTableResponseDtos(tables);

    assertEquals(expected, actual);
  }

  @Test
  void fromTableToTableResponseDto() {
    Table table = new Table(1L, "tbl1", 1, 1, TableStatus.AVAILABLE);

    TableResponseDto expected = new TableResponseDto(table.getId(), table.getName(),
        table.getNumber(), table.getSeats(), table.getTableStatus());

    TableResponseDto actual = underTest.fromTableToTableResponseDto(table);

    assertEquals(expected, actual);
  }

  @Test
  void fromTableCreateRequestToTable() {
    Table expected = new Table(null, "tbl1", 1, 2, TableStatus.AVAILABLE);
    TableCreateRequestDto tableCreateRequestDto = new TableCreateRequestDto("tbl1", 1, 2);

    Table actual = underTest.fromTableCreateRequestToTable(tableCreateRequestDto);

    assertEquals(expected.getId(), actual.getId());
    assertEquals(expected.getName(), actual.getName());
    assertEquals(expected.getNumber(), actual.getNumber());
    assertEquals(expected.getSeats(), actual.getSeats());
    assertEquals(expected.getTableStatus(), actual.getTableStatus());
  }

  @Test
  void fromTableUpdateRequestToTable() {
    Table expected = new Table(null, "tbl1", 1, 2, TableStatus.RESERVED);
    TableUpdateRequestDto updateRequestDto = new TableUpdateRequestDto(1, "tbl1", 1, 2,
        TableStatus.RESERVED);

    Table actual = underTest.fromTableUpdateRequestToTable(updateRequestDto);

    assertEquals(expected.getId(), actual.getId());
    assertEquals(expected.getName(), actual.getName());
    assertEquals(expected.getNumber(), actual.getNumber());
    assertEquals(expected.getSeats(), actual.getSeats());
    assertEquals(expected.getTableStatus(), actual.getTableStatus());
  }
}