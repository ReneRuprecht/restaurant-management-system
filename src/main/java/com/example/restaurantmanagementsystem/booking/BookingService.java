package com.example.restaurantmanagementsystem.booking;

import com.example.restaurantmanagementsystem.booking.Entity.RestaurantTable;
import com.example.restaurantmanagementsystem.booking.exception.NoAvailableTableException;
import com.example.restaurantmanagementsystem.booking.request.BookingRequest;
import com.example.restaurantmanagementsystem.booking.response.BookingResponse;
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

        for (RestaurantTable restaurantTable : restaurantTables) {
            if (restaurantTable.isBooked()) {
                booked.add(restaurantTable);
                continue;
            }
            available.add(restaurantTable);
        }
        return new FindAllBookingResponse(available, booked);
    }

    public BookingResponse bookATable(BookingRequest bookingRequest) {
        RestaurantTable restaurantTable = this.bookingRepository.findByAvailableSeats(bookingRequest.seats())
                                                                .orElseThrow(() -> new NoAvailableTableException(
                                                                        "Alle Tische sind belegt"));

        return new BookingResponse(restaurantTable.getId());
    }
}
