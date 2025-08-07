package com.example.bookingsystem.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String equipment;
    @DateTimeFormat(pattern = "dd/MM/yy") // 保留，適用於表單綁定
    @JsonFormat(pattern = "dd/MM/yy")     // 添加，適用於 JSON 反序列化
    private LocalDate date;
    private String timeSlot;
    private String status;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getEquipment() { return equipment; }
    public void setEquipment(String equipment) { this.equipment = equipment; }
    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }
    public String getTimeSlot() { return timeSlot; }
    public void setTimeSlot(String timeSlot) { this.timeSlot = timeSlot; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Booking() {}

    public Booking(String name, String email, String equipment, LocalDate date, String timeSlot, String status) {
        this.name = name;
        this.email = email;
        this.equipment = equipment;
        this.date = date;
        this.timeSlot = timeSlot;
        this.status = status;
    }
}