package com.example.weather.application;


import com.example.weather.domain.alert.DroughtAlertChecker;
import com.example.weather.domain.alert.HeavyRainAlertChecker;
import com.example.weather.domain.rainfall.DailyRainfall;
import com.example.weather.domain.rainfall.RainfallPeriod;
import com.example.weather.infrastructure.jma.JmaRainfallFetcher;

import java.time.LocalDate;
import java.util.List;

public class WeatherAlertApplication {

    public static void main(String[] args) throws Exception {

        // 1. データ取得
        JmaRainfallFetcher fetcher = new JmaRainfallFetcher();
        List<DailyRainfall> rainfalls =
                fetcher.fetchFromResource("rainfall.csv");

        // ===== 2-A. 集中豪雨アラート（DailyRainfall単位） =====
        HeavyRainAlertChecker heavyRainChecker =
                new HeavyRainAlertChecker(AlertThresholds.HEAVY_RAIN_MM);

        for (DailyRainfall rainfall : rainfalls) {
            heavyRainChecker.check(rainfall)
                    .ifPresent(alert ->
                            System.out.println("ALERT (" + rainfall.getDate() + "): "
                                    + alert.getMessage())
                    );
        }

        // ===== 2-B. 干ばつアラート（期間単位） =====
        DroughtAlertChecker droughtChecker =
                new DroughtAlertChecker(AlertThresholds.DROUGHT_TOTAL_MM);

        int windowSize = AlertThresholds.DROUGHT_DAYS;

        for (int i = 0; i <= rainfalls.size() - windowSize; i++) {

            List<DailyRainfall> window =
                    rainfalls.subList(i, i + windowSize);

            LocalDate startDate = window.get(0).getDate();
            LocalDate endDate = window.get(window.size() - 1).getDate();

            RainfallPeriod period =
                    new RainfallPeriod(startDate, endDate, window);

            droughtChecker.check(period)
                    .ifPresent(alert ->
                            System.out.println(
                                    "ALERT (" + startDate + " ～ " + endDate + "): "
                                            + alert.getMessage()
                            )
                    );
        }


    }
}
