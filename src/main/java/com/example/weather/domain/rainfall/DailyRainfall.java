package com.example.weather.domain.rainfall;

import java.time.LocalDate;
import java.util.Objects;

    /**
     * 1日の降水量を表す値オブジェクト
     */
    public class DailyRainfall {

        private final LocalDate date;
        private final double amountMm;

        public DailyRainfall(LocalDate date, double amountMm) {
            if (amountMm < 0) {
                throw new IllegalArgumentException("降水量は0以上である必要があります");
            }
            this.date = Objects.requireNonNull(date);
            this.amountMm = amountMm;
        }

        public LocalDate getDate() {
            return date;
        }

        public double getAmountMm() {
            return amountMm;
        }
    }
