package com.tharun.lamma.Pitch;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "path_data")
public class PathData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "latitude", nullable = false)
    private double latitude;

    @Column(name = "longitude", nullable = false)
    private double longitude;

    @Column(name = "path_difficulty", nullable = false)
    private int pathDifficulty; // 0 (Easy) to 10 (Very Difficult)

    @Column(name = "hit_count", nullable = false)
    private int hitCount = 1; // Number of times this pothole was hit

    @Column(name = "last_hit", nullable = false)
    private LocalDateTime lastHit = LocalDateTime.now(); // Time of last hit

    @Column(name = "timestamp", nullable = false, updatable = false)
    private LocalDateTime timestamp = LocalDateTime.now(); // Record creation time

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public double getLatitude() { return latitude; }
    public void setLatitude(double latitude) { this.latitude = latitude; }

    public double getLongitude() { return longitude; }
    public void setLongitude(double longitude) { this.longitude = longitude; }

    public int getPathDifficulty() { return pathDifficulty; }
    public void setPathDifficulty(int pathDifficulty) { this.pathDifficulty = pathDifficulty; }

    public int getHitCount() { return hitCount; }
    public void setHitCount(int hitCount) { this.hitCount = hitCount; }

    public LocalDateTime getLastHit() { return lastHit; }
    public void setLastHit(LocalDateTime lastHit) { this.lastHit = lastHit; }

    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
}
