package com.openweathermap.api.service;

import com.openweathermap.api.model.api.ForecastApiResponse;
import com.openweathermap.api.model.api.WeatherApiResponse;
import reactor.core.publisher.Mono;

public interface WeatherService {

    Mono<WeatherApiResponse> getWeatherDetailsByCityName(String cityName);

    Mono<ForecastApiResponse> getWeatherForecastByCityName(String cityName);

}
