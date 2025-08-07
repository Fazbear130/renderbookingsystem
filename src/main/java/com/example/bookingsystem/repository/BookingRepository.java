package com.example.bookingsystem.repository;

import com.example.bookingsystem.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findAll();
    List<Booking> findByEmail(String email);
    // 新增：查询某设备在特定日期和时间段的预约
    List<Booking> findByEquipmentAndDateAndTimeSlot(String equipment, LocalDate date, String timeSlot);
    // 可选：查询某设备某日期的所有预约
    List<Booking> findByEquipmentAndDate(String equipment, LocalDate date);
}