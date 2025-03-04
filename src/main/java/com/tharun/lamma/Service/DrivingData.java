package com.tharun.lamma.Service;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class DrivingData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    private String customerName;
    private double powerUsed;
    private double distance;
    private LocalDateTime chargeStartTime;
    private LocalDateTime chargeEndTime;
    private double efficiency;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }
    public double getPowerUsed() { return powerUsed; }
    public void setPowerUsed(double powerUsed) { this.powerUsed = powerUsed; }
    public double getDistance() { return distance; }
    public void setDistance(double distance) { this.distance = distance; }
    public LocalDateTime getChargeStartTime() { return chargeStartTime; }
    public void setChargeStartTime(LocalDateTime chargeStartTime) { this.chargeStartTime = chargeStartTime; }
    public LocalDateTime getChargeEndTime() { return chargeEndTime; }
    public void setChargeEndTime(LocalDateTime chargeEndTime) { this.chargeEndTime = chargeEndTime; }
    public double getEfficiency() { return efficiency; }
    public void setEfficiency(double efficiency) { this.efficiency = efficiency; }
}