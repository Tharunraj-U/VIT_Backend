package com.tharun.lamma.Pitch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PathDataService {

    @Autowired
    private PathDataRepository repository;

    public void registerPotholeHit(Long userId, double latitude, double longitude) {
        Optional<PathData> existingData = repository.findByLatitudeAndLongitude(latitude, longitude);

        if (existingData.isPresent()) {
            PathData pathData = existingData.get();
            pathData.setHitCount(pathData.getHitCount() + 1);
            pathData.setLastHit(LocalDateTime.now());

            // Dynamically calculate path difficulty
            pathData.setPathDifficulty(calculateDifficultyLevel(pathData.getHitCount()));

            repository.save(pathData);
        } else {
            PathData newData = new PathData();
            newData.setUserId(userId);
            newData.setLatitude(latitude);
            newData.setLongitude(longitude);
            newData.setHitCount(1);
            newData.setLastHit(LocalDateTime.now());

            // Default difficulty for new pothole
            newData.setPathDifficulty(3);

            repository.save(newData);
        }
    }

    // Method to calculate difficulty level based on hit count
    private int calculateDifficultyLevel(int hitCount) {
        if (hitCount >= 8) return 10; // Very difficult
        if (hitCount >= 6) return 8;  // Hard
        if (hitCount >= 4) return 6;  // Moderate
        if (hitCount >= 2) return 4;  // Slightly difficult
        return 3; // Default low difficulty
    }

    public void clearOldHits() {
        LocalDateTime expiryTime = LocalDateTime.now().minusHours(2); // Expiry after 2 hours
        repository.findAll().forEach(data -> {
            if (data.getLastHit().isBefore(expiryTime)) {
                data.setHitCount(0); // Reset count
                repository.save(data);
            }
        });
    }

    public List<PathData> getAllPotholeHits() {
        return repository.findAll();
    }
}
