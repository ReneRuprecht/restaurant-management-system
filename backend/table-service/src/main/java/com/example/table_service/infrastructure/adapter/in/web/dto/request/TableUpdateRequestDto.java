package com.example.table_service.infrastructure.adapter.in.web.dto.request;

import com.example.table_service.domain.model.TableStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;


public record TableUpdateRequestDto(

    @JsonProperty(value = "table_number") @NotNull(message = "table_number must not be null") @Min(value = 1) int tableNumber,

    @NotNull(message = "name must not be null") @NotEmpty @JsonProperty(value = "name") String name,

    @JsonProperty(value = "number") @NotNull(message = "number must not be null") @Min(value = 1) int number,

    @NotNull(message = "seats must not be null") @Min(value = 1) @JsonProperty(value = "seats") int seats,

    @NotNull(message = "table_status must not be null") @JsonProperty(value = "status") TableStatus tableStatus) {


}
