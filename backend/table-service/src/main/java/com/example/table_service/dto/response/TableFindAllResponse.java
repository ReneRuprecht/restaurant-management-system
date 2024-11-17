package com.example.table_service.dto.response;

import com.example.table_service.dto.TableDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public record TableFindAllResponse(

    @JsonProperty(value = "tables") List<TableDto> tables) {

}
