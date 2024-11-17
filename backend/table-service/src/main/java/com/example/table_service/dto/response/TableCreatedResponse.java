package com.example.table_service.dto.response;

import com.example.table_service.dto.TableDto;
import com.fasterxml.jackson.annotation.JsonProperty;

public record TableCreatedResponse(@JsonProperty(value = "table") TableDto tableDto) {

}
