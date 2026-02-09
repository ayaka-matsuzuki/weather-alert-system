package com.example.weather.domain.alert;

import java.time.LocalDate;

public class Alert {

    private final AlertType type;
    private final LocalDate date;
    private final String message;

    public Alert(AlertType type, LocalDate date, String message) {
        this.type = type;
        this.date = date;
        this.message = message;
    }

    public AlertType getType() {
        return type;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getMessage() {
        return message;
    }
}
