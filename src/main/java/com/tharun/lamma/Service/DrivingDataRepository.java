package com.tharun.lamma.Service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface DrivingDataRepository extends JpaRepository<DrivingData, Long> {
    List<DrivingData> findByUserId(Long userId);
    List<DrivingData> findByChargeStartTimeBetween(LocalDate startDate, LocalDate endDate);

    List<DrivingData> findByChargeStartTimeBetween(LocalDateTime localDateTime, LocalDateTime localDateTime1);
}
