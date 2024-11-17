package com.example.table_service.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TableCreateRequest {

  @JsonProperty(value = "name")
  private String name;

  @JsonProperty(value = "number")
  private int number;

  @JsonProperty(value = "seats")
  private int seats;

}
