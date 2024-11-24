package com.example.table_service.infrastructure.adapter.in.web.dto.response;

import com.example.table_service.domain.model.TableStatus;
import com.fasterxml.jackson.annotation.JsonProperty;

public record TableCreatedResponseDto(

    @JsonProperty(value = "id") Long id,

    @JsonProperty(value = "name") String name,

    @JsonProperty(value = "number") int number,

    @JsonProperty(value = "seats") int seats,

    @JsonProperty(value = "table_status") TableStatus tablestatus

) {

}
