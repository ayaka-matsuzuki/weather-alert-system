package com.example.weather.domain.alert;

import com.example.weather.domain.rainfall.RainfallPeriod;

import java.util.Optional;

public class DroughtAlertChecker implements AlertChecker<RainfallPeriod> {

    private final double thresholdMm;

    public DroughtAlertChecker(double thresholdMm) {
        this.thresholdMm = thresholdMm;
    }


    @Override
    public Optional<Alert> check(RainfallPeriod period) {
        if (period.getTotalAmountMm() <= thresholdMm) {
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
