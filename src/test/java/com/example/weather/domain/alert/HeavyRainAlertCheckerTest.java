package com.example.weather.domain.alert;

import com.example.weather.domain.rainfall.DailyRainfall;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class HeavyRainAlertCheckerTest {

    @Test
    // 降水量がしきい値以上ならアラートが返る
    void test1() {
        // given
        DailyRainfall rainfall =
                new DailyRainfall(LocalDate.of(2024, 7, 1), 100.0);
        HeavyRainAlertChecker checker = new HeavyRainAlertChecker();

        // when
        var result = checker.check(rainfall);

        // then
        assertTrue(result.isPresent());
        assertEquals(AlertType.HEAVY_RAIN, result.get().getType());
        assertEquals(LocalDate.of(2024, 7, 1), result.get().getDate());
        assertEquals("Heavy rainfall detected: 100.0mm", result.get().getMessage());
    }

    @Test
    // 降水量がしきい値未満ならアラートは返らない
    void test2() {
        // given
        DailyRainfall rainfall =
                new DailyRainfall(LocalDate.of(2024, 7, 1), 99.9);
        HeavyRainAlertChecker checker = new HeavyRainAlertChecker();

        // when
        var result = checker.check(rainfall);

        // then
        assertTrue(result.isEmpty());
    }
}
