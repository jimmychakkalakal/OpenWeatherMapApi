package com.openweathermap.api.model.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.openweathermap.api.model.City;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ForecastApiResponse {

    @JsonProperty("list")
    private List<WeatherApiResponse> forecastResponses;

    @JsonProperty("city")
    private City city;

}
