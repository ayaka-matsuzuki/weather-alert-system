package com.example.weather.domain.alert;

import java.util.Optional;

public interface AlertChecker<T> {

    Optional<Alert> check(T target);
}
