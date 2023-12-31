package com.example.restaurantmanagementsystem.booking;

import com.example.restaurantmanagementsystem.booking.Entity.RestaurantTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends JpaRepository<RestaurantTable, Long> {
}
