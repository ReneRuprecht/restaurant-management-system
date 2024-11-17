package com.example.table_service.dto.request;

import com.example.table_service.model.TableStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;


public record TableUpdateRequest(@NotEmpty @JsonProperty(value = "name") String name,

                                 @JsonProperty(value = "number") @NotNull(message = "number darf nicht null sein") @Min(value = 1) int number,

                                 @Min(value = 1) @JsonProperty(value = "seats") int seats,

                                 @JsonProperty(value = "status") TableStatus tableStatus) {


}
