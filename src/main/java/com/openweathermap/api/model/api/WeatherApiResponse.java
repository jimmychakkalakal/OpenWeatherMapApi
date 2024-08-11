package com.openweathermap.api.model.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.openweathermap.api.model.Weather;
import com.openweathermap.api.model.WeatherDetails;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WeatherApiResponse {

    @JsonProperty("weather")
    private List<Weather> weatherList;

    @JsonProperty("main")
    private WeatherDetails weatherDetails;

    @JsonProperty("name")
    private String city;

    @JsonProperty("dt_txt")
    private String date;
}
