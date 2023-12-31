package com.example.restaurantmanagementsystem.booking;

import com.example.restaurantmanagementsystem.booking.Entity.RestaurantTable;
import com.example.restaurantmanagementsystem.booking.response.FindAllBookingResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;

    public FindAllBookingResponse findAll() {
        List<RestaurantTable> restaurantTables = this.bookingRepository.findAll();

        List<RestaurantTable> available = new ArrayList<>();
        List<RestaurantTable> booked = new ArrayList<>();

        for (RestaurantTable table : restaurantTables) {
            if (table.isBooked()) {
                booked.add(table);
                continue;
            }
            available.add(table);
        }

        return new FindAllBookingResponse(available, booked);
    }
}
