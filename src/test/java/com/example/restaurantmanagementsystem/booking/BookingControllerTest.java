package com.example.restaurantmanagementsystem.booking;

import com.example.restaurantmanagementsystem.booking.Entity.RestaurantTable;
import com.example.restaurantmanagementsystem.booking.response.FindAllBookingsResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookingController.class)
class BookingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private BookingService bookingService;

    @Test
    void shouldReturnFindAllBookingsResponseWithStatus200() throws Exception {

        List<RestaurantTable> available = List.of(new RestaurantTable(1L, 1L, 4, false));

        List<RestaurantTable> booked = List.of(new RestaurantTable(2L, 2L, 3, true),
                                               new RestaurantTable(3L, 3L, 1, true));

        FindAllBookingsResponse findAllBookingsResponse = new FindAllBookingsResponse(available,
                                                                                      booked);

        when(bookingService.findAll()).thenReturn(findAllBookingsResponse);

        String expected = objectMapper.writeValueAsString(findAllBookingsResponse);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/booking"))
               .andExpect(status().isOk())
               .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
               .andExpect(content().string(expected))
               .andDo(print());

        verify(bookingService, times(1)).findAll();
    }

    @Test
    void shouldReturnFindAllBookingsResponseWithEmptyListsAndStatus200() throws Exception {

        List<RestaurantTable> available = List.of(new RestaurantTable(1L, 1L, 4, false));

        List<RestaurantTable> booked = List.of(new RestaurantTable(2L, 2L, 3, true),
                                               new RestaurantTable(3L, 3L, 1, true));

        FindAllBookingsResponse findAllBookingsResponse = new FindAllBookingsResponse(available,
                                                                                      booked);

        when(bookingService.findAll()).thenReturn(findAllBookingsResponse);

        String expected = objectMapper.writeValueAsString(findAllBookingsResponse);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/booking"))
               .andExpect(status().isOk())
               .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
               .andExpect(content().string(expected))
               .andDo(print());

        verify(bookingService, times(1)).findAll();
    }

}