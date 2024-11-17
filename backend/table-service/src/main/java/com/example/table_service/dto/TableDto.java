package com.example.table_service.dto;

import com.example.table_service.model.TableStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class TableDto {

  @JsonProperty(value = "name")
  String name;

  @JsonProperty(value = "number")
  int number;

  @JsonProperty(value = "seats")
  int seats;

  @JsonProperty(value = "status")
  TableStatus tableStatus;

}
