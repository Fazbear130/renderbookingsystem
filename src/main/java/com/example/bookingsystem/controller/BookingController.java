package com.example.bookingsystem.controller;

import com.example.bookingsystem.model.Booking;
import com.example.bookingsystem.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {
    @Autowired
    private BookingRepository bookingRepository;

    @PostMapping
    public ResponseEntity<Booking> createBooking(@RequestBody Booking booking) {
        try {
            // 解析日期和时间段
            LocalDate bookingDate = booking.getDate();
            String timeSlot = booking.getTimeSlot(); // 格式如 "09:00-10:00"
            String[] times = timeSlot.split("-");
            LocalTime startTime = LocalTime.parse(times[0], DateTimeFormatter.ofPattern("HH:mm"));
            LocalTime endTime = LocalTime.parse(times[1], DateTimeFormatter.ofPattern("HH:mm"));

            // 检查时间段冲突
            List<Booking> existingBookings = bookingRepository.findByEquipmentAndDate(
                    booking.getEquipment(), bookingDate);
            for (Booking existing : existingBookings) {
                String[] existingTimes = existing.getTimeSlot().split("-");
                LocalTime existingStart = LocalTime.parse(existingTimes[0], DateTimeFormatter.ofPattern("HH:mm"));
                LocalTime existingEnd = LocalTime.parse(existingTimes[1], DateTimeFormatter.ofPattern("HH:mm"));
                // 判断时间段是否重叠
                if (!(endTime.isBefore(existingStart) || startTime.isAfter(existingEnd))) {
                    return ResponseEntity.status(HttpStatus.CONFLICT)
                            .body(null); // 返回冲突错误
                }
            }
            return ResponseEntity.ok(bookingRepository.save(booking));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping
    public ResponseEntity<List<Booking>> getAllBookings() {
        try {
            return ResponseEntity.ok(bookingRepository.findAll());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }

    @GetMapping("/availability")
    public ResponseEntity<List<Booking>> getBookingsByEquipmentAndDate(
            @RequestParam String equipment,
            @RequestParam String date) {
        try {
            LocalDate localDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("dd/MM/yy"));
            List<Booking> bookings = bookingRepository.findByEquipmentAndDate(equipment, localDate);
            return ResponseEntity.ok(bookings);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
}