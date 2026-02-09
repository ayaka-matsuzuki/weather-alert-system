package com.example.weather.domain.alert;

import com.example.weather.domain.rainfall.RainfallPeriod;

import java.util.Optional;

public class DroughtAlertChecker implements AlertChecker<RainfallPeriod> {

    private static final double THRESHOLD_MM = 10.0;

    @Override
    public Optional<Alert> check(RainfallPeriod period) {
        if (period.getTotalAmountMm() <= THRESHOLD_MM) {
            return Optional.of(
                    new Alert(
                            AlertType.DROUGHT,
                            period.getEndDate(),
                            "Drought detected: total " + period.getTotalAmountMm() + "mm"
                    )
            );
        }
        return Optional.empty();
    }
}
