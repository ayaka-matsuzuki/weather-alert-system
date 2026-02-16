package com.example.weather.domain.alert;

import com.example.weather.domain.rainfall.DailyRainfall;

import java.util.Optional;

public class HeavyRainAlertChecker implements AlertChecker<DailyRainfall> {

    private final double thresholdMm;

    public HeavyRainAlertChecker(double thresholdMm) {
        this.thresholdMm = thresholdMm;
    }

    @Override
    public Optional<Alert> check(DailyRainfall rainfall) {
        if (rainfall.getAmountMm() >= thresholdMm) {
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
