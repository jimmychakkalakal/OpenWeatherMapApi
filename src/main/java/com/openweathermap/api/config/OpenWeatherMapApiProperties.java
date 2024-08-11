package com.openweathermap.api.config;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("open.weather.map.api")
@Getter
public class OpenWeatherMapApiProperties {

    private final String baseUrl = "https://api.openweathermap.org/data/2.5/";

    private final String weather_temperature_endpoint = "/weather?q={cityName}&units=metric&APPID=";

    private final String weather_forecast_endpoint = "/forecast?q={cityName}&units=metric&APPID=";

    @Setter
    @NonNull
    private String apiKey;

}
