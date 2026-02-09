package com.example.weather.domain.alert;

import com.example.weather.domain.rainfall.DailyRainfall;
import com.example.weather.domain.rainfall.RainfallPeriod;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DroughtAlertCheckerTest {

    @Test
    // 期間合計がしきい値以下なら干ばつアラートが返る
    void test1() {
        // given
        LocalDate start = LocalDate.of(2024, 6, 1);
        LocalDate end = LocalDate.of(2024, 6, 30);

        List<DailyRainfall> dailyRainfalls = List.of(
                new DailyRainfall(start, 5.0),
                new DailyRainfall(start.plusDays(1), 5.0)
        );

        RainfallPeriod period =
                new RainfallPeriod(start, end, dailyRainfalls);

        DroughtAlertChecker checker = new DroughtAlertChecker();

        // when
        var result = checker.check(period);

        // then
        assertTrue(result.isPresent());
        assertEquals(AlertType.DROUGHT, result.get().getType());
        assertEquals(LocalDate.of(2024, 6, 30), result.get().getDate());
        assertEquals("Drought detected: total 10.0mm", result.get().getMessage());
    }

    @Test
    // 期間合計がしきい値を超える場合はアラートが返らない
    void test2() {
        // given
        LocalDate start = LocalDate.of(2024, 6, 1);
        LocalDate end = LocalDate.of(2024, 6, 30);

        List<DailyRainfall> dailyRainfalls = List.of(
                new DailyRainfall(start, 10.0),
                new DailyRainfall(start.plusDays(1), 0.1)
        );

        RainfallPeriod period =
                new RainfallPeriod(start, end, dailyRainfalls);

        DroughtAlertChecker checker = new DroughtAlertChecker();

        // when
        var result = checker.check(period);

        // then
        assertTrue(result.isEmpty());
    }
}
