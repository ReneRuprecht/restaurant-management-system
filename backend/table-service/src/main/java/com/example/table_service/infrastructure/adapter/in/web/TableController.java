package com.example.table_service.infrastructure.adapter.in.web;

import com.example.table_service.application.usecase.DeleteTableByNumberUseCase;
import com.example.table_service.application.usecase.FindAllTableUseCase;
import com.example.table_service.application.usecase.FindTableByNumberUseCase;
import com.example.table_service.application.usecase.SaveTableUseCase;
import com.example.table_service.application.usecase.UpdateTableUseCase;
import com.example.table_service.domain.exception.TableWithNumberAlreadyExistsException;
import com.example.table_service.domain.model.Table;
import com.example.table_service.infrastructure.adapter.in.web.dto.request.TableCreateRequestDto;
import com.example.table_service.infrastructure.adapter.in.web.dto.request.TableUpdateRequestDto;
import com.example.table_service.infrastructure.adapter.in.web.dto.response.TableCreatedResponseDto;
import com.example.table_service.infrastructure.adapter.in.web.dto.response.TableResponseDto;
import com.example.table_service.infrastructure.adapter.in.web.mapper.TableDtoMapper;
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

  private final FindAllTableUseCase findAllTableUseCase;
  private final FindTableByNumberUseCase findTableByNumberUseCase;
  private final SaveTableUseCase saveTableUseCase;
  private final DeleteTableByNumberUseCase deleteTableByNumberUseCase;
  private final UpdateTableUseCase updateTableUseCase;

  private final TableDtoMapper tableDtoMapper;

  @GetMapping
  public ResponseEntity<List<TableResponseDto>> findAll() {

    List<Table> tables = findAllTableUseCase.execute();

    return new ResponseEntity<>(tableDtoMapper.fromTablesToTableResponseDtos(tables),
        HttpStatus.OK);
  }

  @GetMapping(path = "/{number}")
  public ResponseEntity<TableResponseDto> findTableByNumber(@PathVariable int number) {

    Table table = findTableByNumberUseCase.execute(number);

    TableResponseDto response = tableDtoMapper.fromTableToTableResponseDto(table);

    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<TableCreatedResponseDto> create(
      @RequestBody TableCreateRequestDto tableCreateRequestDto)
      throws TableWithNumberAlreadyExistsException {

    Table tableToSave = tableDtoMapper.fromTableCreateRequestToTable(tableCreateRequestDto);
    Table savedTable = saveTableUseCase.execute(tableToSave);

    return new ResponseEntity<>(tableDtoMapper.fromTableToTableCreatedResponseDto(savedTable),
        HttpStatus.CREATED);
  }

  @DeleteMapping(path = "/{number}")
  public ResponseEntity<String> deleteTableByNumber(@PathVariable int number) {

    deleteTableByNumberUseCase.execute(number);
    String message = String.format("Table with number %d was deleted successfully", number);

    return new ResponseEntity<>(message, HttpStatus.OK);
  }

  @PutMapping
  public ResponseEntity<TableResponseDto> update(
      @Valid @RequestBody TableUpdateRequestDto tableUpdateRequestDto) {

    Table table = tableDtoMapper.fromTableUpdateRequestToTable(tableUpdateRequestDto);

    Table updatedtable = updateTableUseCase.execute(tableUpdateRequestDto.tableNumber(), table);

    TableResponseDto response = tableDtoMapper.fromTableToTableResponseDto(updatedtable);

    return new ResponseEntity<>(response, HttpStatus.OK);
  }


}
