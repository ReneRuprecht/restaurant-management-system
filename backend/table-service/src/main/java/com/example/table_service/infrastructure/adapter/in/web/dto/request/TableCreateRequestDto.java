package com.example.table_service.infrastructure.adapter.in.web.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TableCreateRequestDto {

  @JsonProperty(value = "name")
  private String name;

  @JsonProperty(value = "number")
  private int number;

  @JsonProperty(value = "seats")
  private int seats;

}
