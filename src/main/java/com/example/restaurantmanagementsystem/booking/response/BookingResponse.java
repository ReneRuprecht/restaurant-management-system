package com.example.restaurantmanagementsystem.booking.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record BookingResponse(
        @JsonProperty(value = "table_id")
        Long tableId) {
}
