package com.example.restaurantmanagementsystem.booking;

import com.example.restaurantmanagementsystem.booking.request.BookingRequest;
import com.example.restaurantmanagementsystem.booking.response.BookingResponse;
import com.example.restaurantmanagementsystem.booking.response.FindAllBookingsResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/booking")
@AllArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @GetMapping
    public ResponseEntity<FindAllBookingsResponse> findAll() {
        return new ResponseEntity<>(this.bookingService.findAll(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<BookingResponse> bookATable(@RequestBody BookingRequest bookingRequest) {
        return new ResponseEntity<>(this.bookingService.bookATable(bookingRequest),
                                    HttpStatus.CREATED);
    }
}
