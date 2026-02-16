package com.example.weather.infrastructure.jma;

import com.example.weather.domain.rainfall.DailyRainfall;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class JmaRainfallFetcher {

    private static final DateTimeFormatter DATE_FORMAT =
            DateTimeFormatter.ofPattern("yyyy/M/d");

    private static final Charset SHIFT_JIS =
            Charset.forName("Shift_JIS");

    public List<DailyRainfall> fetchFromResource(String resourceName)
            throws Exception {
        // CSVを読み込む
        // LocalDate + 降水量(mm)
        // DailyRainfall に変換
        List<DailyRainfall> result = new ArrayList<>();

        InputStream is = getClass()
                .getClassLoader()
                .getResourceAsStream(resourceName);

        if (is == null) {
            throw new IllegalArgumentException(
                    "Resource not found: " + resourceName);
        }

        try (BufferedReader reader =
                     new BufferedReader(
                             new InputStreamReader(is, SHIFT_JIS))) {

            String line;
            while ((line = reader.readLine()) != null) {
                if (line.isBlank()) {
                    continue;
                }

                String[] cols = line.split(",");

                // 列数不足はスキップ
                if (cols.length < 2) {
                    continue;
                }

                try {
                    LocalDate date =
                            LocalDate.parse(cols[0].trim(), DATE_FORMAT);
                    double rainfall =
                            Double.parseDouble(cols[1].trim());

                    result.add(new DailyRainfall(date, rainfall));

                } catch (Exception e) {
                    // 日付や数値でない行（ヘッダ・説明行）は無視
                    continue;
                }
            }
        }

        return result;
    }
}

