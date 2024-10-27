package com.example.table_service.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record TableCreateRequest(

    @JsonProperty(value = "name") String name,

    @JsonProperty(value = "display_number") int displayNumber,

    @JsonProperty(value = "seats") int seats) {

}
