package com.openweathermap.api.controller;

import com.openweathermap.api.model.WeatherResponseInternal;
import com.openweathermap.api.model.api.ForecastApiResponse;
import com.openweathermap.api.model.api.WeatherApiResponse;
import com.openweathermap.api.service.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class OWMController {

    private final WeatherService service;


    @GetMapping("/day/{city}")
    public Mono<WeatherResponseInternal> getTemperatureByCityName(@PathVariable("city") String cityName) {
        return service.getWeatherDetailsByCityName(cityName)
                .map(this::mapExternalResponseToInternal);
    }

    @GetMapping("/week/{city}")
    public Mono<List<WeatherResponseInternal>> getTemperatureForecastByCityName(@PathVariable("city") String cityName) {
        return service.getWeatherForecastByCityName(cityName)
                .map(this::mapExternalResponseToInternal)
                .flatMap(Mono::just);
    }

    @GetMapping("/view-weather/{city}")
    public ModelAndView viewWeatherByCityName(@PathVariable("city") String cityName) {
        WeatherResponseInternal weatherDetailsByCityName = service.getWeatherDetailsByCityName(cityName)
                .map(this::mapExternalResponseToInternal)
                .share().block();
        ModelAndView mav = new ModelAndView("view-weather");
        mav.addObject("weather", weatherDetailsByCityName);
        return mav;
    }

    private List<WeatherResponseInternal> mapExternalResponseToInternal(ForecastApiResponse forecastApiResponse) {

        List<WeatherResponseInternal> result = new ArrayList<>();
        for (WeatherApiResponse eachWeatherApiResponse : forecastApiResponse.getForecastResponses()) {
            WeatherResponseInternal weatherResponseInternal = WeatherResponseInternal.builder()
                    .city(forecastApiResponse.getCity().getName())
                    .temperature(eachWeatherApiResponse.getWeatherDetails().getTemperature())
                    .icon(eachWeatherApiResponse.getWeatherList().get(0).getIcon())
                    .date(eachWeatherApiResponse.getDate())
                    .build();
            result.add(weatherResponseInternal);
        }
        return result;
    }

    private WeatherResponseInternal mapExternalResponseToInternal(WeatherApiResponse weatherApiResponse) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/uuuu");
        LocalDate localDate = LocalDate.now();
        return WeatherResponseInternal.builder()
                .icon(weatherApiResponse.getWeatherList().get(0).getIcon())
                .city(weatherApiResponse.getCity())
                .date(dtf.format(localDate))
                .temperature(weatherApiResponse.getWeatherDetails().getTemperature())
                .build();
    }

}
