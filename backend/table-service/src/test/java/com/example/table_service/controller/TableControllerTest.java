package com.example.table_service.controller;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.table_service.dto.TableDto;
import com.example.table_service.dto.response.TableFindAllResponse;
import com.example.table_service.exception.TableNotFoundException;
import com.example.table_service.service.TableServiceImpl;
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

@WebMvcTest(TableController.class) // Teste den UserController
@ExtendWith(MockitoExtension.class)
class TableControllerTest {

  @Autowired
  private MockMvc mockMvc; // MockMvc für den Test

  @MockBean
  private TableServiceImpl tableService;

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
        TableDto.builder().name("tbl1").displayNumber(1).seats(2).build(),
        TableDto.builder().name("tbl2").displayNumber(2).seats(3).build());

    TableFindAllResponse expected = new TableFindAllResponse(tables);

    when(tableService.findAllTables()).thenReturn(tables);

    mockMvc.perform(get("/api/v1/table").accept(MediaType.APPLICATION_JSON))
        .andExpect(status().is2xxSuccessful())
        .andExpect(content().json(objectMapper.writeValueAsString(expected)));

    verify(tableService, times(1)).findAllTables();
  }

}