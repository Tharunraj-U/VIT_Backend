package com.tharun.lamma.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;

@Service
public class DrivingDataService {

    @Autowired
    private DrivingDataRepository drivingDataRepository; // Fetches user driving records

    public String getDrivingSummary(Long userId) {
        List<DrivingData> drivingRecords = drivingDataRepository.findByUserId(userId);

        if (drivingRecords.isEmpty()) {
            return "No driving data available.";
        }

        // Process EV-specific data
        double totalDistance = drivingRecords.stream().mapToDouble(DrivingData::getDistance).sum();
        double totalPowerUsed = drivingRecords.stream().mapToDouble(DrivingData::getPowerUsed).sum();
        double avgEfficiency = totalPowerUsed > 0 ? totalDistance / totalPowerUsed : 0; // Avoid division by zero

        // Calculate total charging duration
        long totalChargingMinutes = drivingRecords.stream()
                .filter(record -> record.getChargeStartTime() != null && record.getChargeEndTime() != null)
                .mapToLong(record -> Duration.between(record.getChargeStartTime(), record.getChargeEndTime()).toMinutes())
                .sum();

        // Generate summary
        String summary = String.format("Total distance driven: %.2f km. Power used: %.2f kWh. "
                        + "Average efficiency: %.2f km/kWh. Total charging time: %d minutes.",
                totalDistance, totalPowerUsed, avgEfficiency, totalChargingMinutes);

        // Provide efficiency insights
        if (avgEfficiency < 5) {
            summary += " Consider optimizing acceleration and regenerative braking to improve efficiency.";
        } else {
            summary += " Great job maintaining efficient driving habits!";
        }

        return summary;
    }
}
