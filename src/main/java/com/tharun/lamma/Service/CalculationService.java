package com.tharun.lamma.Service;

import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;

@Service
public class CalculationService {

    private static final double PETROL_CONSUMPTION_PER_KM = 0.05; // 5L per 100km
    private static final double PETROL_COST_PER_LITER = 100.0; // Adjust as per real-time value
    private static final double EV_CONSUMPTION_PER_KM = 0.2; // 200Wh per km
    private static final double ELECTRICITY_COST_PER_KWH = 8.0; // Adjust as per real-time value
    private static final double CO2_EMISSION_PER_LITER_PETROL = 2.31; // 2.31kg CO₂ per liter of petrol

    public Map<String, String> calculateSavings(double kilometers, double electricityUsed) {
        // Petrol vehicle cost calculation
        double petrolUsed = kilometers * PETROL_CONSUMPTION_PER_KM;
        double petrolCost = petrolUsed * PETROL_COST_PER_LITER;

        // EV cost calculation
        double evCost = electricityUsed * ELECTRICITY_COST_PER_KWH;

        // Cost Savings
        double savings = petrolCost - evCost;

        // Environmental Impact Calculation
        double co2Saved = petrolUsed * CO2_EMISSION_PER_LITER_PETROL;

        // Create result message
        String message = String.format("✅ You just saved ₹%.2f and reduced %.2f kg of CO₂ emissions compared to a petrol vehicle!", savings, co2Saved);
        Map<String, String> result = new HashMap<>();
        result.put("message", message);
        return result;
    }
}
