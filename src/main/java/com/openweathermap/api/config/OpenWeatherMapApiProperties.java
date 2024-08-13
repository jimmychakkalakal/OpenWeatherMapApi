package com.openweathermap.api.config;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

@Configuration
@ConfigurationProperties("open.weather.map.api")
@Validated
@Data
public class OpenWeatherMapApiProperties {

    private String baseUrl = "https://api.openweathermap.org/data/2.5";

    private String weather_temperature_endpoint = "/weather?q={cityName}&units=metric&APPID=";

    private String weather_forecast_endpoint = "/forecast?q={cityName}&units=metric&APPID=";

    @NotBlank
    private String apiKey;

}
