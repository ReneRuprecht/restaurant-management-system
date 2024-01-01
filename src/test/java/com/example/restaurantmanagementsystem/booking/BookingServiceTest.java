package com.example.restaurantmanagementsystem.booking;

import com.example.restaurantmanagementsystem.booking.Entity.RestaurantTable;
import com.example.restaurantmanagementsystem.booking.exception.NoAvailableTableException;
import com.example.restaurantmanagementsystem.booking.request.BookingRequest;
import com.example.restaurantmanagementsystem.booking.response.BookingResponse;
import com.example.restaurantmanagementsystem.booking.response.FindAllBookingsResponse;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookingServiceTest {

    @Mock
    private BookingRepository bookingRepository;

    @InjectMocks
    private BookingService underTest;

    @Test
    void shouldReturnFindAllWithoutData() {

        FindAllBookingsResponse expected = new FindAllBookingsResponse(new ArrayList<>(),
                                                                       new ArrayList<>());

        when(bookingRepository.findAll()).thenReturn(List.of());

        FindAllBookingsResponse actual = underTest.findAll();

        verify(bookingRepository, times(1)).findAll();

        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    void shouldReturnFindAllBookingsResponseWithData() {

        List<RestaurantTable> available = List.of(new RestaurantTable(1L, 1L, 4, false));

        List<RestaurantTable> booked = List.of(new RestaurantTable(2L, 2L, 3, true),
                                               new RestaurantTable(3L, 3L, 1, true));

        List<RestaurantTable> restaurantTables = Stream.concat(available.stream(), booked.stream())
                                                       .toList();

        FindAllBookingsResponse expected = new FindAllBookingsResponse(available, booked);

        when(bookingRepository.findAll()).thenReturn(restaurantTables);

        FindAllBookingsResponse actual = underTest.findAll();

        verify(bookingRepository, times(1)).findAll();

        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    void shouldThrowNoAvailableTableExceptionIfThereIsNoTableAvailable() {

        BookingRequest bookingRequest = new BookingRequest(3);
        String expectedMessage = "Alle Tische sind belegt";
        when(bookingRepository.findByAvailableSeats(bookingRequest.seats())).thenReturn(Optional.empty());

        NoAvailableTableException actual = assertThrows(NoAvailableTableException.class, () -> {
            underTest.bookATable(bookingRequest);
        });

        verify(bookingRepository, times(1)).findByAvailableSeats(bookingRequest.seats());

        assertEquals(expectedMessage, actual.getMessage());
    }

    @Test
    void shouldReturnBookingResponseWithTableIdIfThereIsATableAvailable() {

        BookingResponse expected = new BookingResponse(1L);

        BookingRequest bookingRequest = new BookingRequest(3);
        RestaurantTable restaurantTable = new RestaurantTable(1L, 1L, 5, false);
        RestaurantTable savedRestaurantTable = new RestaurantTable(1L, 1L, 5, true);

        when(bookingRepository.findByAvailableSeats(bookingRequest.seats())).thenReturn(Optional.of(
                restaurantTable));


        when(bookingRepository.save(restaurantTable)).thenReturn(savedRestaurantTable);

        BookingResponse actual = underTest.bookATable(bookingRequest);

        verify(bookingRepository, times(1)).findByAvailableSeats(bookingRequest.seats());
        verify(bookingRepository, times(1)).save(savedRestaurantTable);

        assertNotNull(actual);
        assertEquals(expected, actual);
    }
}