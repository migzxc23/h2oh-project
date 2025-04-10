package com.h2oh.project.controller;

import com.h2oh.project.entity.Booking;
import com.h2oh.project.svc.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    // Create a booking
    @PostMapping("/create")
    public Booking createBooking(@RequestParam Long userId, @RequestParam String bookingDetails) {
        return bookingService.createBooking(userId, bookingDetails);
    }

    // Get bookings for a user
    @GetMapping("/user/{userId}")
    public List<Booking> getBookings(@PathVariable Long userId) {
        return bookingService.getBookingsByUserId(userId);
    }
}
