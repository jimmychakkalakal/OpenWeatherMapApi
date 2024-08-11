package com.openweathermap.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WeatherResponseInternal {

    private BigDecimal temperature;
    private String city;
    private String icon;
    private String date;
}
