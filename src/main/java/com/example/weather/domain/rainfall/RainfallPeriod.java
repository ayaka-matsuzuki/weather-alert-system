package com.example.weather.domain.rainfall;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class RainfallPeriod {

    private final LocalDate startDate;
    private final LocalDate endDate;
    private final List<DailyRainfall> dailyRainfalls;

    public RainfallPeriod(
            LocalDate startDate,
            LocalDate endDate,
            List<DailyRainfall> dailyRainfalls
    ) {
        this.startDate = Objects.requireNonNull(startDate);
        this.endDate = Objects.requireNonNull(endDate);

        Objects.requireNonNull(dailyRainfalls);

        if (dailyRainfalls.isEmpty()) {
            throw new IllegalArgumentException("dailyRainfalls must not be empty");
        }

        if (dailyRainfalls.stream().anyMatch(Objects::isNull)) {
            throw new IllegalArgumentException("dailyRainfalls must not contain null");
        }

        if (dailyRainfalls.stream().anyMatch(r ->
                r.getDate().isBefore(startDate)
                        || r.getDate().isAfter(endDate))) {
            throw new IllegalArgumentException("dailyRainfalls contains data outside period");
        }

        this.dailyRainfalls = List.copyOf(dailyRainfalls);
    }

    public double getTotalAmountMm() {
        return dailyRainfalls.stream()
                .mapToDouble(DailyRainfall::getAmountMm)
                .sum();
    }
    public LocalDate getEndDate() {
        return endDate;
    }
}

