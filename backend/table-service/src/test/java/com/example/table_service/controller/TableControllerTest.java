package com.example.table_service.controller;

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

import com.example.table_service.dto.TableDto;
import com.example.table_service.dto.request.TableCreateRequest;
import com.example.table_service.dto.request.TableUpdateRequest;
import com.example.table_service.dto.response.TableCreatedResponse;
import com.example.table_service.dto.response.TableFindAllResponse;
import com.example.table_service.dto.response.TableUpdatedResponse;
import com.example.table_service.exception.TableNotFoundException;
import com.example.table_service.exception.TableWithNumberAlreadyExistsException;
import com.example.table_service.model.TableStatus;
import com.example.table_service.service.TableServiceImpl;
import com.example.table_service.util.TableMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
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

  @Autowired
  private MockMvc mockMvc; // MockMvc für den Test

  @MockBean
  private TableServiceImpl tableService;

  @MockBean
  private TableMapper tableMapper;

  private ObjectMapper objectMapper;

  @BeforeEach
  void setUp() {
    objectMapper = new ObjectMapper();
  }

  @Test
  void findAll_whenTablesDoNotExist_throwsTablesNotFoundExceptionAndStatus404() throws Exception {

    String expectedMessage = "Es wurden keine Tische gefunden";

    when(tableService.findAllTables()).thenThrow(new TableNotFoundException(expectedMessage));

    mockMvc.perform(get("/api/v1/table").accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound()).andExpect(content().string(expectedMessage));

    verify(tableService, times(1)).findAllTables();
  }

  @Test
  void findAll_whenTablesDoExist_returnTablesAndStatus200() throws Exception {

    List<TableDto> tables = List.of(
        TableDto.builder().name("tbl1").number(1).seats(2).tableStatus(TableStatus.AVAILABLE)
            .build(),
        TableDto.builder().name("tbl2").number(2).seats(3).tableStatus(TableStatus.RESERVED)
            .build());

    TableFindAllResponse expected = new TableFindAllResponse(tables);

    when(tableService.findAllTables()).thenReturn(tables);

    mockMvc.perform(get("/api/v1/table").accept(MediaType.APPLICATION_JSON))
        .andExpect(status().is2xxSuccessful())
        .andExpect(content().json(objectMapper.writeValueAsString(expected)));

    verify(tableService, times(1)).findAllTables();
  }

  @Test
  void create_whenTableWithNumberAlreadyExists_throwsTableWithNumberAlreadyExistsExceptionAndStatus419()
      throws Exception {

    TableDto tableDto = TableDto.builder().name("tbl1").number(1).seats(3)
        .tableStatus(TableStatus.AVAILABLE).build();
    TableCreateRequest tableCreateRequest = new TableCreateRequest("tbl1", 1, 3);

    String expectedMessage = String.format("Der Tisch mit der Nummer %d existiert bereits",
        tableDto.getNumber());

    when(tableMapper.fromTableCreateRequestToTableDto(tableCreateRequest)).thenReturn(tableDto);

    when(tableService.create(tableDto)).thenThrow(
        new TableWithNumberAlreadyExistsException(expectedMessage));

    mockMvc.perform(post("/api/v1/table").contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(tableCreateRequest)))
        .andExpect(status().isConflict()).andExpect(content().string(expectedMessage));
  }

  @Test
  void create_whenTableWithNumberDoesNotAlreadyExists_returnTableCreateResponseAndStatus201()
      throws Exception {

    TableCreateRequest tableCreateRequest = new TableCreateRequest("tbl1", 1, 3);

    TableDto tableDto = TableDto.builder().name("tbl1").number(1).seats(3).build();

    TableCreatedResponse expected = new TableCreatedResponse(tableDto);

    when(tableMapper.fromTableCreateRequestToTableDto(tableCreateRequest)).thenReturn(tableDto);

    when(tableService.create(tableDto)).thenReturn(tableDto);

    mockMvc.perform(post("/api/v1/table").contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(tableCreateRequest)))
        .andExpect(status().isCreated())
        .andExpect(content().json(objectMapper.writeValueAsString(expected)));

    verify(tableService, times(1)).create(tableDto);
  }

  @Test
  void findTableByNumber_whenTableWithNumberDoesNotExists_throwsTableNotFoundExceptionExceptionAndStatus404()
      throws Exception {

    TableDto tableDto = TableDto.builder().name("tbl1").number(1).seats(3)
        .tableStatus(TableStatus.AVAILABLE).build();

    String expectedMessage = String.format("Der Tisch mit der Nummer %d wurde nicht gefunden",
        tableDto.getNumber());

    when(tableService.findTableByNumber(tableDto.getNumber())).thenThrow(
        new TableNotFoundException(expectedMessage));

    mockMvc.perform(get(String.format("/api/v1/table/%d", tableDto.getNumber())))
        .andExpect(status().isNotFound()).andExpect(content().string(expectedMessage));
  }

  @Test
  void findTableByNumber_whenTableWithNumberExists_returnTableDtoAndStatus200() throws Exception {

    TableDto expected = TableDto.builder().name("tbl1").number(1).seats(3)
        .tableStatus(TableStatus.AVAILABLE).build();

    when(tableService.findTableByNumber(expected.getNumber())).thenReturn(expected);

    mockMvc.perform(get(String.format("/api/v1/table/%d", expected.getNumber())))
        .andExpect(status().isOk())
        .andExpect(content().json(objectMapper.writeValueAsString(expected)));

    verify(tableService, times(1)).findTableByNumber(expected.getNumber());
  }

  @Test
  void deleteTableByNumber_whenTableWithNumberDoesNotExists_throwsTableNotFoundExceptionExceptionAndStatus404()
      throws Exception {

    TableDto tableDto = TableDto.builder().name("tbl1").number(1).seats(3)
        .tableStatus(TableStatus.AVAILABLE).build();

    String expectedMessage = String.format("Der Tisch mit der Nummer %d wurde nicht gefunden",
        tableDto.getNumber());

    doThrow(new TableNotFoundException(expectedMessage)).when(tableService)
        .deleteTableByNumber(tableDto.getNumber());

    mockMvc.perform(delete(String.format("/api/v1/table/%d", tableDto.getNumber())))
        .andExpect(status().isNotFound()).andExpect(content().string(expectedMessage));
  }

  @Test
  void deleteTableByNumber_whenTableWithNumberDoesExists_returnsStatus200() throws Exception {

    TableDto tableDto = TableDto.builder().name("tbl1").number(1).seats(3)
        .tableStatus(TableStatus.AVAILABLE).build();

    mockMvc.perform(delete(String.format("/api/v1/table/%d", tableDto.getNumber())))
        .andExpect(status().isOk());

    verify(tableService, times(1)).deleteTableByNumber(tableDto.getNumber());
  }

  @Test
  void updateTable_whenTableWithNumberDoesExists_returnsStatus200() throws Exception {

    TableUpdateRequest tableUpdateRequest = new TableUpdateRequest("tbl1_updated", 1, 4,
        TableStatus.AVAILABLE);

    TableDto tableDtoUpdated = TableDto.builder().name("tbl1_updated").number(1).seats(4)
        .tableStatus(TableStatus.RESERVED).build();

    TableUpdatedResponse tableUpdatedResponse = new TableUpdatedResponse(tableDtoUpdated);

    when(tableMapper.fromTableUpdateRequestToTableDto(tableUpdateRequest)).thenReturn(
        tableDtoUpdated);

    when(tableService.update(tableDtoUpdated)).thenReturn(tableDtoUpdated);

    mockMvc.perform(put("/api/v1/table").contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(tableUpdateRequest))).andExpect(status().isOk())
        .andExpect(content().json(objectMapper.writeValueAsString(tableUpdatedResponse)));

    verify(tableService, times(1)).update(tableDtoUpdated);
  }

  @Test
  void updateTable_whenTableSeatsAreInvalid_returnsStatus400() throws Exception {

    TableUpdateRequest tableUpdateRequest = new TableUpdateRequest("tbl1_updated", 1, 0,
        TableStatus.AVAILABLE);

    String invalidSeats = "{\"seats\":\"must be greater than or equal to 1\"}";

    mockMvc.perform(
            put("/api/v1/table").contentType(MediaType.APPLICATION_JSON).contentType("application/json")
                .content(objectMapper.writeValueAsString(tableUpdateRequest)))
        .andExpect(status().isBadRequest()).andExpect(content().string(invalidSeats));
  }

  @Test
  void updateTable_whenTableNameIsEmpty_returnsStatus400() throws Exception {

    TableUpdateRequest tableUpdateRequest = new TableUpdateRequest("", 1, 1, TableStatus.AVAILABLE);

    String invalidSeats = "{\"name\":\"must not be empty\"}";

    mockMvc.perform(
            put("/api/v1/table").contentType(MediaType.APPLICATION_JSON).contentType("application/json")
                .content(objectMapper.writeValueAsString(tableUpdateRequest)))
        .andExpect(status().isBadRequest()).andExpect(content().string(invalidSeats));
  }
}