package com.example.restaurantmanagementsystem.booking.response;

import com.example.restaurantmanagementsystem.booking.Entity.RestaurantTable;

import java.util.List;


public record FindAllBookingsResponse(List<RestaurantTable> available,
                                      List<RestaurantTable> booked) {
}
