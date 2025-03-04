package com.tharun.lamma.Service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@CrossOrigin("*")
@RequestMapping("/api")
public class ChatController {

    private final ChatClient chatClient;
    private final CalculationService calculationService;
    private final NotificationService notificationService;

    @Autowired
    private DrivingDataRepository drivingDataRepository;

    public ChatController(ChatClient chatClient, CalculationService calculationService, NotificationService notificationService) {
        this.chatClient = chatClient;
        this.calculationService = calculationService;
        this.notificationService = notificationService;
    }

    @PostMapping("/calculate-savings")
    public String calculateSavings(@RequestParam("email") String email,
                                   @RequestParam("kilometers") double kilometers,
                                   @RequestParam("electricityUsed") double electricityUsed) {
        Map<String, String> result = calculationService.calculateSavings(kilometers, electricityUsed);
        notificationService.sendSavingsNotification(email, result.get("message"));
        return result.get("message");
    }





    @GetMapping("/chat")
    public ResponseEntity<Map<String, Object>> chat(@RequestParam("message") String message,
                                                    @RequestParam(value = "userId", required = false) Long userId) {
        String promptMessage = "User query: " + message;
        String drivingData = null;
        if (userId != null) {
            drivingData = drivingDataRepository.findByUserId(userId).toString();
            promptMessage += ". Driving data: " + drivingData;
        }

        String response = chatClient.prompt().user(promptMessage).call().content();
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("message", message);
        responseBody.put("userId", userId);
        responseBody.put("drivingData", drivingData);
        responseBody.put("chatResponse", response);
        return ResponseEntity.ok(responseBody);
    }

    @PostMapping("/chat-with-image")
    public String chatWithImage(@RequestPart("message") String message,
                                @RequestPart("image") MultipartFile file) {
        return chatClient.prompt()
                .user(prompt -> prompt.text(message).media(MimeTypeUtils.IMAGE_PNG, new InputStreamResource(file)))
                .call().content();
    }
}
