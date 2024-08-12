package com.openweathermap.api.controller;

import com.openweathermap.api.model.City;
import com.openweathermap.api.model.Weather;
import com.openweathermap.api.model.WeatherDetails;
import com.openweathermap.api.model.WeatherResponseInternal;
import com.openweathermap.api.model.api.ForecastApiResponse;
import com.openweathermap.api.model.api.WeatherApiResponse;
import com.openweathermap.api.service.WeatherService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.servlet.ModelAndView;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OWMControllerTest {

    private OWMController controller;

    @Mock
    private WeatherService service;

    @BeforeEach
    void setUp() {
        controller = new OWMController(service);
    }

    @Test
    void shouldReturnWeatherByCityName() {

        WeatherApiResponse weatherResponse = getWeatherApiResponse();

        when(service.getWeatherDetailsByCityName("London")).thenReturn(Mono.just(weatherResponse));

        Mono<WeatherResponseInternal> result = controller.getTemperatureByCityName("London");

        WeatherResponseInternal weatherResponseInternal = result.share().block();
        assertEquals("London", weatherResponseInternal.getCity());
        assertEquals(294.86, weatherResponseInternal.getTemperature().doubleValue());
        assertEquals("01d", weatherResponseInternal.getIcon());
    }


    @Test
    void shouldReturnForecastByCityName() {

        ForecastApiResponse forecastApiResponse = ForecastApiResponse.builder()
                .forecastResponses(List.of(getWeatherApiResponse()))
                .city(City.builder()
                        .name("London")
                        .build())
                .build();

        when(service.getWeatherForecastByCityName("London")).thenReturn(Mono.just(forecastApiResponse));

        Mono<List<WeatherResponseInternal>> result = controller.getTemperatureForecastByCityName("London");
        List<WeatherResponseInternal> responseInternal = result.block();

        assertEquals(1, responseInternal.size());

        WeatherResponseInternal weatherResponseInternal = responseInternal.get(0);

        assertEquals("London", weatherResponseInternal.getCity());
        assertEquals(294.86, weatherResponseInternal.getTemperature().doubleValue());
        assertEquals("01d", weatherResponseInternal.getIcon());
        assertEquals("11.08.2024", weatherResponseInternal.getDate());
    }

    @Test
    void shouldReturnAViewModelForACity() {

        WeatherApiResponse weatherResponse = getWeatherApiResponse();

        when(service.getWeatherDetailsByCityName("London")).thenReturn(Mono.just(weatherResponse));

        ModelAndView modelAndView = controller.viewWeatherByCityName("London");
        modelAndView.getViewName();
        assertEquals("view-weather", modelAndView.getViewName());

        WeatherResponseInternal weatherResponseInternal = (WeatherResponseInternal) modelAndView.getModel().get("weather");

        assertNotNull(weatherResponseInternal);
        assertEquals("London", weatherResponseInternal.getCity());
        assertEquals(294.86, weatherResponseInternal.getTemperature().doubleValue());
        assertEquals("01d", weatherResponseInternal.getIcon());

    }

    private static WeatherApiResponse getWeatherApiResponse() {
        return WeatherApiResponse.builder()
                .city("London")
                .weatherDetails(WeatherDetails.builder()
                        .temperature(BigDecimal.valueOf(294.86))
                        .build())
                .weatherList(List.of(Weather.builder()
                        .icon("01d")
                        .build()))
                .date("11.08.2024")
                .build();
    }
}