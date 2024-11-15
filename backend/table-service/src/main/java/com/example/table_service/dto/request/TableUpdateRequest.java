package com.example.table_service.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;


public record TableUpdateRequest(@NotEmpty @JsonProperty(value = "name") String name,

                                 @JsonProperty(value = "display_number") @NotNull(message = "display_number darf nicht null sein") @Min(value = 1) int displayNumber,

                                 @Min(value = 1) @JsonProperty(value = "seats") int seats) {


}
