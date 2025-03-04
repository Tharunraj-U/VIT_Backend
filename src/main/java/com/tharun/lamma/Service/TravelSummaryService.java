package com.tharun.lamma.Service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TravelSummaryService {

    private final DrivingDataRepository drivingDataRepository;
    private final CalculationService calculationService;
    private final JavaMailSender mailSender;

    public TravelSummaryService(DrivingDataRepository drivingDataRepository,
                                CalculationService calculationService,
                                JavaMailSender mailSender) {
        this.drivingDataRepository = drivingDataRepository;
        this.calculationService = calculationService;
        this.mailSender = mailSender;
    }

    @Scheduled(cron = "0 0 20 * * ?") // Runs every day at 8 PM
    public void sendDailyTravelSummary() {
        LocalDate today = LocalDate.now();

        // Fetch today's driving data
        List<DrivingData> logs = drivingDataRepository.findByChargeStartTimeBetween(
                today.atStartOfDay(), today.plusDays(1).atStartOfDay());

        // Group by user email
        Map<String, List<DrivingData>> userLogs = logs.stream()
                .collect(Collectors.groupingBy(DrivingData::getEmail));

        // Send email to each user
        for (Map.Entry<String, List<DrivingData>> entry : userLogs.entrySet()) {
            String email = entry.getKey();
            List<DrivingData> userTrips = entry.getValue();

            double totalKm = userTrips.stream().mapToDouble(DrivingData::getDistance).sum();
            double totalElectricity = userTrips.stream().mapToDouble(DrivingData::getPowerUsed).sum();

            // Calculate savings
            Map<String, String> savingsResult = calculationService.calculateSavings(totalKm, totalElectricity);

            // Send email
            sendSavingsNotification(email, totalKm, totalElectricity, savingsResult.get("message"));
        }
    }

    private void sendSavingsNotification(String email, double totalKm, double totalElectricity, String savingsMessage) {
        String subject = "🚗 Your Daily EV Savings Summary ⚡";

        String message = String.format(
                "Hello,\n\n🚗 Today, you drove %.2f km using your EV, consuming %.2f kWh of electricity.\n\n" +
                        "🌱 %s\n\n" +
                        "🔎 If you had used a petrol vehicle, you would have spent more money and released more CO₂!\n\n" +
                        "🚀 Keep driving green! Your efforts matter. 🌍⚡\n\nBest,\nEV Savings Team",
                totalKm, totalElectricity, savingsMessage
        );

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(email);
        mailMessage.setSubject(subject);
        mailMessage.setText(message);

        mailSender.send(mailMessage);
    }
}
