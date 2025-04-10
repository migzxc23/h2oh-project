package com.h2oh.project.svc;

import com.h2oh.project.entity.Booking;
import com.h2oh.project.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    public Booking createBooking(Long userId, String bookingDetails) {
        Booking booking = new Booking();
        booking.setUserId(userId);
        booking.setBookingDate(LocalDateTime.now());
        booking.setBookingDetails(bookingDetails);
        return bookingRepository.save(booking);
    }

    public List<Booking> getBookingsByUserId(Long userId) {
        return bookingRepository.findByUserId(userId);
    }
}
