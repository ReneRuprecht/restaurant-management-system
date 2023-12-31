package com.example.restaurantmanagementsystem.booking;

import com.example.restaurantmanagementsystem.booking.response.FindAllBookingResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/booking")
@AllArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @GetMapping
    public ResponseEntity<FindAllBookingResponse> findAll() {
        return new ResponseEntity<>(this.bookingService.findAll(), HttpStatus.OK);
    }
}
