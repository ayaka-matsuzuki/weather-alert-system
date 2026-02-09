package com.example.weather.domain.alert;

import com.example.weather.domain.rainfall.DailyRainfall;

import java.util.Optional;

public class HeavyRainAlertChecker implements AlertChecker<DailyRainfall> {

    private static final double THRESHOLD_MM = 100.0;

    @Override
    public Optional<Alert> check(DailyRainfall rainfall) {
        if (rainfall.getAmountMm() >= THRESHOLD_MM) {
            return Optional.of(
                    new Alert(
                            AlertType.HEAVY_RAIN,
                            rainfall.getDate(),
                            "Heavy rainfall detected: " + rainfall.getAmountMm() + "mm"
                    )
            );
        }
        return Optional.empty();
    }
}
