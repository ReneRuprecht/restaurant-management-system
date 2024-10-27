package com.example.table_service.dto;

import com.example.table_service.entity.Table;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record TableDto(

    @JsonProperty(value = "display_number") int displayNumber,

    @JsonProperty(value = "name") String name,

    @JsonProperty(value = "seats") int seats) {


  public static TableDto fromEntity(Table table) {
    if (table == null) {
      return null;
    }
    return TableDto.builder().name(table.getName()).displayNumber(table.getDisplayNumber())
        .seats(table.getSeats()).build();
  }

}
