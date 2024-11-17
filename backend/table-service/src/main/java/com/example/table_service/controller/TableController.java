package com.example.table_service.controller;

import com.example.table_service.dto.TableDto;
import com.example.table_service.dto.request.TableCreateRequest;
import com.example.table_service.dto.request.TableUpdateRequest;
import com.example.table_service.dto.response.TableCreatedResponse;
import com.example.table_service.dto.response.TableFindAllResponse;
import com.example.table_service.dto.response.TableUpdatedResponse;
import com.example.table_service.exception.TableNotFoundException;
import com.example.table_service.exception.TableWithNumberAlreadyExistsException;
import com.example.table_service.service.TableService;
import com.example.table_service.util.TableMapper;
import jakarta.validation.Valid;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/table")
@AllArgsConstructor
public class TableController {

  private final TableService tableService;

  private final TableMapper tableMapper;

  @GetMapping()
  public ResponseEntity<TableFindAllResponse> findAllTables() throws TableNotFoundException {
    List<TableDto> tables = tableService.findAllTables();

    return new ResponseEntity<>(new TableFindAllResponse(tables), HttpStatus.OK);
  }

  @GetMapping("/{number}")
  public ResponseEntity<TableDto> findTableByNumber(@PathVariable int number)
      throws TableNotFoundException {
    TableDto table = tableService.findTableByNumber(number);

    return new ResponseEntity<>(table, HttpStatus.OK);
  }

  @DeleteMapping("/{number}")
  public ResponseEntity<Void> deleteTableByNumber(@PathVariable int number)
      throws TableNotFoundException {
    tableService.deleteTableByNumber(number);

    return new ResponseEntity<>(HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<TableCreatedResponse> create(
      @RequestBody TableCreateRequest tableCreateRequest)
      throws TableWithNumberAlreadyExistsException {

    TableDto tableToCreate = tableMapper.fromTableCreateRequestToTableDto(tableCreateRequest);

    TableDto createdTableDto = tableService.create(tableToCreate);

    return new ResponseEntity<>(new TableCreatedResponse(createdTableDto), HttpStatus.CREATED);
  }

  @PutMapping
  public ResponseEntity<TableUpdatedResponse> update(
      @Valid @RequestBody TableUpdateRequest tableUpdateRequest) throws TableNotFoundException {

    TableDto tableToUpdate = tableMapper.fromTableUpdateRequestToTableDto(tableUpdateRequest);

    TableDto updatedTableDto = tableService.update(tableToUpdate);

    return new ResponseEntity<>(new TableUpdatedResponse(updatedTableDto), HttpStatus.OK);
  }

}
