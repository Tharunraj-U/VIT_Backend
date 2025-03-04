package com.tharun.lamma.Pitch;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PathDataRepository extends JpaRepository<PathData, Long> {
    Optional<PathData> findByLatitudeAndLongitude(double latitude, double longitude);
}
