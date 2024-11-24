package com.example.table_service.infrastructure.adapter.in.web;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.table_service.application.usecase.DeleteTableByNumberUseCase;
import com.example.table_service.application.usecase.FindAllTableUseCase;
import com.example.table_service.application.usecase.FindTableByNumberUseCase;
import com.example.table_service.application.usecase.SaveTableUseCase;
import com.example.table_service.application.usecase.UpdateTableUseCase;
import com.example.table_service.domain.exception.TableNotFoundException;
import com.example.table_service.domain.exception.TableWithNumberAlreadyExistsException;
import com.example.table_service.domain.model.Table;
import com.example.table_service.domain.model.TableStatus;
import com.example.table_service.infrastructure.adapter.in.web.dto.request.TableCreateRequestDto;
import com.example.table_service.infrastructure.adapter.in.web.dto.request.TableUpdateRequestDto;
import com.example.table_service.infrastructure.adapter.in.web.dto.response.TableCreatedResponseDto;
import com.example.table_service.infrastructure.adapter.in.web.dto.response.TableResponseDto;
import com.example.table_service.infrastructure.adapter.in.web.mapper.TableDtoMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(TableController.class)
@ExtendWith(MockitoExtension.class)
class TableControllerTest {


  @MockBean
  FindTableByNumberUseCase findTableByNumberUseCase;

  @MockBean
  FindAllTableUseCase findAllTableUseCase;

  @MockBean
  SaveTableUseCase saveTableUseCase;

  @MockBean
  DeleteTableByNumberUseCase deleteTableByNumberUseCase;

  @MockBean
  UpdateTableUseCase updateTableUseCase;

  @MockBean
  TableDtoMapper tableDtoMapper;

  @Autowired
  private MockMvc mockMvc;

  private ObjectMapper objectMapper;

  @BeforeEach
  void setUp() {
    objectMapper = new ObjectMapper();
  }

  @Test
  void findAll_whenTablesDoNotExist_returnsEmptyList() throws Exception {

    when(findAllTableUseCase.execute()).thenReturn(Collections.emptyList());

    mockMvc.perform(get("/api/v1/table").accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk()).andExpect(content().json("[]"));

    verify(findAllTableUseCase, times(1)).execute();
  }

  @Test
  void findAll_whenTablesDoExist_returnTablesAndStatus200() throws Exception {

    Table tableOne = new Table(1L, "tbl1", 1, 3, TableStatus.AVAILABLE);
    Table tableTwo = new Table(2L, "tbl2", 2, 3, TableStatus.RESERVED);

    List<TableResponseDto> expected = List.of(
        new TableResponseDto(1L, "tbl1", 1, 3, TableStatus.AVAILABLE),
        new TableResponseDto(2L, "tbl2", 2, 3, TableStatus.RESERVED)

    );

    when(tableDtoMapper.fromTablesToTableResponseDtos(List.of(tableOne, tableTwo))).thenReturn(
        expected);
    when(findAllTableUseCase.execute()).thenReturn(List.of(tableOne, tableTwo));

    mockMvc.perform(get("/api/v1/table").accept(MediaType.APPLICATION_JSON))
        .andExpect(status().is2xxSuccessful())
        .andExpect(content().json(objectMapper.writeValueAsString(expected)));

    verify(findAllTableUseCase, times(1)).execute();
    verify(tableDtoMapper, times(1)).fromTablesToTableResponseDtos(List.of(tableOne, tableTwo));
  }


  @Test
  void findTableByNumber_whenTableWithNumberDoesNotExists_throwsTableNotFoundExceptionExceptionAndStatus404()
      throws Exception {

    Table table = new Table(1L, "tbl1", 1, 3, TableStatus.AVAILABLE);

    String expectedMessage = String.format("Table with number %d does not exists",
        table.getNumber());

    when(findTableByNumberUseCase.execute(table.getNumber())).thenThrow(
        new TableNotFoundException(expectedMessage));

    mockMvc.perform(get(String.format("/api/v1/table/%d", table.getNumber())))
        .andExpect(status().isNotFound()).andExpect(content().string(expectedMessage));
  }

  @Test
  void findTableByNumber_whenTableWithNumberExists_returnTableResponseDtoAndStatus200()
      throws Exception {

    Table table = new Table(1L, "tbl1", 1, 3, TableStatus.AVAILABLE);
    TableResponseDto expectedResponse = new TableResponseDto(1L, "tbl1", 1, 3,
        TableStatus.AVAILABLE);

    when(findTableByNumberUseCase.execute(table.getNumber())).thenReturn(table);
    when(tableDtoMapper.fromTableToTableResponseDto(table)).thenReturn(expectedResponse);

    mockMvc.perform(get(String.format("/api/v1/table/%d", table.getNumber())))
        .andExpect(status().isOk())
        .andExpect(content().json(objectMapper.writeValueAsString(expectedResponse)));

    verify(findTableByNumberUseCase, times(1)).execute(table.getNumber());
    verify(tableDtoMapper, times(1)).fromTableToTableResponseDto(table);
  }


  @Test
  void create_whenTableWithNumberAlreadyExists_throwsTableWithNumberAlreadyExistsExceptionAndStatus419()
      throws Exception {

    Table table = new Table(1L, "tbl1", 1, 3, TableStatus.AVAILABLE);
    TableCreateRequestDto tableCreateRequestDto = new TableCreateRequestDto("tbl1", 1, 3);

    String expectedMessage = String.format("Table with number %d already exists",
        tableCreateRequestDto.getNumber());

    when(tableDtoMapper.fromTableCreateRequestToTable(tableCreateRequestDto)).thenReturn(table);

    when(saveTableUseCase.execute(table)).thenThrow(
        new TableWithNumberAlreadyExistsException(expectedMessage));

    mockMvc.perform(post("/api/v1/table").contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(tableCreateRequestDto)))
        .andExpect(status().isConflict()).andExpect(content().string(expectedMessage));
  }

  @Test
  void create_whenTableWithNumberDoesNotAlreadyExists_returnTableCreateResponseAndStatus201()
      throws Exception {

    TableCreateRequestDto tableCreateRequestDto = new TableCreateRequestDto("tbl1", 1, 3);

    Table table = new Table(1L, "tbl1", 1, 3, TableStatus.AVAILABLE);

    TableCreatedResponseDto expected = new TableCreatedResponseDto(table.getId(), table.getName(),
        table.getNumber(), table.getSeats(), table.getTableStatus());

    when(tableDtoMapper.fromTableCreateRequestToTable(tableCreateRequestDto)).thenReturn(table);
    when(saveTableUseCase.execute(table)).thenReturn(table);
    when(tableDtoMapper.fromTableToTableCreatedResponseDto(table)).thenReturn(expected);

    mockMvc.perform(post("/api/v1/table").contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(tableCreateRequestDto)))
        .andExpect(status().isCreated())
        .andExpect(content().json(objectMapper.writeValueAsString(expected)));

    verify(saveTableUseCase, times(1)).execute(table);
    verify(tableDtoMapper, times(1)).fromTableToTableCreatedResponseDto(table);
    verify(tableDtoMapper, times(1)).fromTableCreateRequestToTable(tableCreateRequestDto);
  }


  @Test
  void deleteTableByNumber_whenTableWithNumberDoesNotExists_throwsTableNotFoundExceptionExceptionAndStatus404()
      throws Exception {

    Table table = new Table(1L, "tbl1", 1, 3, TableStatus.AVAILABLE);

    String expectedMessage = String.format("Table with number %d does not exists",
        table.getNumber());

    doThrow(new TableNotFoundException(expectedMessage)).when(deleteTableByNumberUseCase)
        .execute(table.getNumber());

    mockMvc.perform(delete(String.format("/api/v1/table/%d", table.getNumber())))
        .andExpect(status().isNotFound()).andExpect(content().string(expectedMessage));
  }

  @Test
  void deleteTableByNumber_whenTableWithNumberDoesExists_returnsStatus200() throws Exception {

    Table table = new Table(1L, "tbl1", 1, 3, TableStatus.AVAILABLE);

    String message = String.format("Table with number %d was deleted successfully",
        table.getNumber());

    mockMvc.perform(delete(String.format("/api/v1/table/%d", table.getNumber())))
        .andExpect(status().isOk()).andExpect(content().string(message));

    verify(deleteTableByNumberUseCase, times(1)).execute(table.getNumber());
  }

  @Test
  void updateTable_whenTableWithNumberDoesExist_returnsStatus200() throws Exception {

    TableUpdateRequestDto tableUpdateRequestDto = new TableUpdateRequestDto(1, "tbl2", 2, 5,
        TableStatus.RESERVED);

    Table tableFromRequest = new Table(null, tableUpdateRequestDto.name(),
        tableUpdateRequestDto.number(), tableUpdateRequestDto.seats(),
        tableUpdateRequestDto.tableStatus());

    Table expectedTable = new Table(1L, "tbl2", 2, 5, TableStatus.RESERVED);
    TableResponseDto expectedResponse = new TableResponseDto(expectedTable.getId(),
        expectedTable.getName(), expectedTable.getNumber(), expectedTable.getSeats(),
        expectedTable.getTableStatus());

    when(tableDtoMapper.fromTableUpdateRequestToTable(tableUpdateRequestDto)).thenReturn(
        tableFromRequest);

    when(updateTableUseCase.execute(tableUpdateRequestDto.tableNumber(),
        tableFromRequest)).thenReturn(expectedTable);

    when(tableDtoMapper.fromTableToTableResponseDto(expectedTable)).thenReturn(expectedResponse);

    mockMvc.perform(put("/api/v1/table").contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(tableUpdateRequestDto))).andExpect(status().isOk())
        .andExpect(content().json(objectMapper.writeValueAsString(expectedResponse)));

    verify(updateTableUseCase, times(1)).execute(tableUpdateRequestDto.tableNumber(),
        tableFromRequest);

    verify(tableDtoMapper, times(1)).fromTableUpdateRequestToTable(tableUpdateRequestDto);
    verify(tableDtoMapper, times(1)).fromTableToTableResponseDto(expectedTable);
  }

  @Test
  void updateTable_whenTableSeatsAreInvalid_returnsStatus400() throws Exception {

    TableUpdateRequestDto tableUpdateRequestDto = new TableUpdateRequestDto(1, "tbl2", 2, 0,
        TableStatus.RESERVED);

    String expectedMessage = "{\"seats\":\"must be greater than or equal to 1\"}";

    mockMvc.perform(
            put("/api/v1/table").contentType(MediaType.APPLICATION_JSON).contentType("application/json")
                .content(objectMapper.writeValueAsString(tableUpdateRequestDto)))
        .andExpect(status().isBadRequest()).andExpect(content().string(expectedMessage));
  }

  @Test
  void updateTable_whenTableNameIsEmpty_returnsStatus400() throws Exception {

    TableUpdateRequestDto tableUpdateRequestDto = new TableUpdateRequestDto(1, "", 2, 1,
        TableStatus.RESERVED);

    String expectedMessage = "{\"name\":\"must not be empty\"}";

    mockMvc.perform(
            put("/api/v1/table").contentType(MediaType.APPLICATION_JSON).contentType("application/json")
                .content(objectMapper.writeValueAsString(tableUpdateRequestDto)))
        .andExpect(status().isBadRequest()).andExpect(content().string(expectedMessage));
  }

  @Test
  void updateTable_whenTableNumberIsInvalid_returnsStatus400() throws Exception {

    TableUpdateRequestDto tableUpdateRequestDto = new TableUpdateRequestDto(0, "tbl1", 2, 1,
        TableStatus.RESERVED);

    String expectedMessage = "{\"tableNumber\":\"must be greater than or equal to 1\"}";

    mockMvc.perform(
            put("/api/v1/table").contentType(MediaType.APPLICATION_JSON).contentType("application/json")
                .content(objectMapper.writeValueAsString(tableUpdateRequestDto)))
        .andExpect(status().isBadRequest()).andExpect(content().string(expectedMessage));
  }

  @Test
  void updateTable_whenTableNumberDoesNotExist_throwsTableNotFoundExceptionAndStatus404()
      throws Exception {

    TableUpdateRequestDto tableUpdateRequestDto = new TableUpdateRequestDto(1, "tbl1", 2, 1,
        TableStatus.RESERVED);

    Table tableFromRequest = new Table(null, tableUpdateRequestDto.name(),
        tableUpdateRequestDto.number(), tableUpdateRequestDto.seats(),
        tableUpdateRequestDto.tableStatus());

    String expectedMessage = String.format("Table with number %d does not exists",
        tableUpdateRequestDto.tableNumber());

    when(tableDtoMapper.fromTableUpdateRequestToTable(tableUpdateRequestDto)).thenReturn(
        tableFromRequest);

    doThrow(new TableNotFoundException(expectedMessage)).when(updateTableUseCase)
        .execute(tableUpdateRequestDto.tableNumber(), tableFromRequest);

    mockMvc.perform(
            put("/api/v1/table").contentType(MediaType.APPLICATION_JSON).contentType("application/json")
                .content(objectMapper.writeValueAsString(tableUpdateRequestDto)))
        .andExpect(status().isNotFound()).andExpect(content().string(expectedMessage));


  }

  @Test
  void updateTable_whenTableNumberDoesAlreadyExist_throwsTableAlreadyExistExceptionAndStatus409()
      throws Exception {

    TableUpdateRequestDto tableUpdateRequestDto = new TableUpdateRequestDto(1, "tbl1", 2, 1,
        TableStatus.RESERVED);

    Table tableFromRequest = new Table(null, tableUpdateRequestDto.name(),
        tableUpdateRequestDto.number(), tableUpdateRequestDto.seats(),
        tableUpdateRequestDto.tableStatus());

    String expectedMessage = String.format("Table with number %d already exist",
        tableUpdateRequestDto.tableNumber());

    when(tableDtoMapper.fromTableUpdateRequestToTable(tableUpdateRequestDto)).thenReturn(
        tableFromRequest);

    doThrow(new TableWithNumberAlreadyExistsException(expectedMessage)).when(updateTableUseCase)
        .execute(tableUpdateRequestDto.tableNumber(), tableFromRequest);

    mockMvc.perform(
            put("/api/v1/table").contentType(MediaType.APPLICATION_JSON).contentType("application/json")
                .content(objectMapper.writeValueAsString(tableUpdateRequestDto)))
        .andExpect(status().isConflict()).andExpect(content().string(expectedMessage));


  }

}