package com.example.restaurantmanagementsystem.booking;

import com.example.restaurantmanagementsystem.booking.Entity.RestaurantTable;
import com.example.restaurantmanagementsystem.booking.exception.NoAvailableTableException;
import com.example.restaurantmanagementsystem.booking.request.BookingRequest;
import com.example.restaurantmanagementsystem.booking.response.BookingResponse;
import com.example.restaurantmanagementsystem.booking.response.FindAllBookingResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookingServiceTest {

    @Mock
    private BookingRepository bookingRepository;

    @InjectMocks
    private BookingService underTest;

    @Test
    void shouldReturnFindAllWithoutData() {

        FindAllBookingResponse expected = new FindAllBookingResponse(new ArrayList<>(),
                                                                     new ArrayList<>());

        when(bookingRepository.findAll()).thenReturn(List.of());

        FindAllBookingResponse actual = underTest.findAll();

        assertNotNull(actual);

        assertEquals(expected, actual);
    }

    @Test
    void shouldReturnFindAllWithData() {

        List<RestaurantTable> available = List.of(new RestaurantTable(1L, 1L, 4, false));

        List<RestaurantTable> booked = List.of(new RestaurantTable(2L, 2L, 3, true),
                                               new RestaurantTable(3L, 3L, 1, true));

        List<RestaurantTable> table = Stream.concat(available.stream(), booked.stream()).toList();

        FindAllBookingResponse expected = new FindAllBookingResponse(available, booked);

        when(bookingRepository.findAll()).thenReturn(table);

        FindAllBookingResponse actual = underTest.findAll();

        assertNotNull(actual);

        assertEquals(expected, actual);
    }

    @Test
    void shouldThrowNoAvailableTableExceptionIfThereIsNoTableAvailable() {

        BookingRequest bookingRequest = new BookingRequest(3);
        String expectedMessage = "Alle Tische sind belegt";
        when(bookingRepository.findByAvailableSeats(anyInt())).thenReturn(Optional.empty());

        NoAvailableTableException actual = assertThrows(NoAvailableTableException.class, () -> {
            underTest.bookATable(bookingRequest);
        });

        assertEquals(expectedMessage, actual.getMessage());
    }

    @Test
    void shouldReturnTableIdIfThereIsATableAvailable() {

        BookingResponse expected = new BookingResponse(1L);

        BookingRequest bookingRequest = new BookingRequest(3);
        RestaurantTable restaurantTable = new RestaurantTable(1L, 1L, 5, false);

        when(bookingRepository.findByAvailableSeats(bookingRequest.seats())).thenReturn(Optional.of(
                restaurantTable));

        BookingResponse actual = underTest.bookATable(bookingRequest);

        assertNotNull(actual);
        assertEquals(expected, actual);
    }
}