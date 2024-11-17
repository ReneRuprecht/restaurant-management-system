package com.example.table_service.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.example.table_service.dto.TableDto;
import com.example.table_service.dto.request.TableCreateRequest;
import com.example.table_service.dto.request.TableUpdateRequest;
import com.example.table_service.entity.Table;
import com.example.table_service.model.TableStatus;
import org.junit.jupiter.api.Test;

class TableMapperTest {

  TableMapper underTest = new TableMapper();

  @Test
  void fromTableDtoToEntity() {
    TableDto tableDto = TableDto.builder().name("tbl1").number(1).seats(1)
        .tableStatus(TableStatus.RESERVED).build();
    Table expected = Table.builder().name("tbl1").number(1).seats(1)
        .tableStatus(TableStatus.RESERVED).build();

    Table actual = underTest.fromTableDtoToEntity(tableDto);

    assertEquals(expected.getName(), actual.getName());
    assertEquals(expected.getNumber(), actual.getNumber());
    assertEquals(expected.getSeats(), actual.getSeats());
    assertEquals(expected.getTableStatus(), actual.getTableStatus());

  }

  @Test
  void fromTableToTableDto() {
    Table table = Table.builder().name("tbl1").number(1).seats(1).tableStatus(TableStatus.RESERVED)
        .build();
    TableDto expected = TableDto.builder().name("tbl1").number(1).seats(1)
        .tableStatus(TableStatus.RESERVED).build();

    TableDto actual = underTest.fromTableToTableDto(table);

    assertEquals(expected.getName(), actual.getName());
    assertEquals(expected.getNumber(), actual.getNumber());
    assertEquals(expected.getSeats(), actual.getSeats());
    assertEquals(expected.getTableStatus(), actual.getTableStatus());
  }

  @Test
  void fromTableCreateRequestToTableDto() {
    TableCreateRequest tableCreateRequest = new TableCreateRequest("tbl1", 1, 1);
    TableDto expected = TableDto.builder().name("tbl1").number(1).seats(1).build();

    TableDto actual = underTest.fromTableCreateRequestToTableDto(tableCreateRequest);

    assertEquals(expected.getName(), actual.getName());
    assertEquals(expected.getNumber(), actual.getNumber());
    assertEquals(expected.getSeats(), actual.getSeats());
    assertEquals(TableStatus.AVAILABLE, actual.getTableStatus());
  }

  @Test
  void fromTableUpdateRequestToTableDto() {
    TableUpdateRequest tableUpdateRequest = new TableUpdateRequest("tbl1", 1, 1,
        TableStatus.RESERVED);
    TableDto expected = TableDto.builder().name("tbl1").number(1).seats(1)
        .tableStatus(TableStatus.RESERVED).build();

    TableDto actual = underTest.fromTableUpdateRequestToTableDto(tableUpdateRequest);

    assertEquals(expected.getName(), actual.getName());
    assertEquals(expected.getNumber(), actual.getNumber());
    assertEquals(expected.getSeats(), actual.getSeats());
    assertEquals(expected.getTableStatus(), actual.getTableStatus());
  }
}