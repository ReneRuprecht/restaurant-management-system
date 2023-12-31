package com.example.restaurantmanagementsystem.booking;

import com.example.restaurantmanagementsystem.booking.Entity.RestaurantTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<RestaurantTable, Long> {
    @Query(value = "SELECT * FROM restaurant_table r WHERE r.available_seats>=?1 AND r.booked=FALSE",
            nativeQuery = true)
    Optional<RestaurantTable> findByAvailableSeats(int seats);
}
