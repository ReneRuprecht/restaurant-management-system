package com.example.table_service.controller;

import com.example.table_service.dto.TableDto;
import com.example.table_service.dto.request.TableCreateRequest;
import com.example.table_service.dto.response.TableCreatedResponse;
import com.example.table_service.dto.response.TableFindAllResponse;
import com.example.table_service.service.TableService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/table")
@AllArgsConstructor
public class TableController {

  private final TableService tableService;

  @GetMapping()
  public ResponseEntity<TableFindAllResponse> findAllTables() {
    List<TableDto> tables = tableService.findAllTables();

    return new ResponseEntity<>(new TableFindAllResponse(tables), HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<TableCreatedResponse> create(
      @RequestBody TableCreateRequest tableCreateRequest) {

    TableDto tableToCreate = TableDto.builder().name(tableCreateRequest.name())
        .displayNumber(tableCreateRequest.displayNumber()).seats(tableCreateRequest.seats())
        .build();

    TableDto createdTableDto = tableService.create(tableToCreate);

    return new ResponseEntity<>(new TableCreatedResponse(createdTableDto), HttpStatus.CREATED);
  }

}
