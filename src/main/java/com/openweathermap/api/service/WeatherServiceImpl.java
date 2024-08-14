package com.openweathermap.api.service;

import com.openweathermap.api.config.OpenWeatherMapApiProperties;
import com.openweathermap.api.model.api.ForecastApiResponse;
import com.openweathermap.api.model.api.WeatherApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class WeatherServiceImpl implements WeatherService {

    private final OpenWeatherMapApiProperties properties;

    private final WebClient webClient;

    public WeatherServiceImpl(OpenWeatherMapApiProperties properties, WebClient.Builder webClientBuilder) {
        this.properties = properties;
        this.webClient = webClientBuilder.baseUrl(properties.getBaseUrl()).build();
    }

    public Mono<WeatherApiResponse> getWeatherDetailsByCityName(String cityName) {
        log.info("Calling OpenWeatherMap Weather Api to obtain Temperature for City {}", cityName);

        return this.webClient.get()
                .uri(properties.getWeather_temperature_endpoint() + properties.getApiKey(), cityName)
                .retrieve()
                .bodyToMono(WeatherApiResponse.class);
    }

    public Mono<ForecastApiResponse> getWeatherForecastByCityName(String cityName) {
        log.info("Calling OpenWeatherMap Forecast Api to obtain Temperature Forecast for City {}", cityName);

        return this.webClient.get()
                .uri(properties.getWeather_forecast_endpoint() + properties.getApiKey(), cityName)
                .retrieve()
                .bodyToMono(ForecastApiResponse.class);
    }

}
