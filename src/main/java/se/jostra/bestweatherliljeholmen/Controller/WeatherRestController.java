package se.jostra.bestweatherliljeholmen.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import se.jostra.bestweatherliljeholmen.data.Weather;
import se.jostra.bestweatherliljeholmen.service.WeatherService;

@RestController
public class WeatherRestController {
    @Autowired
    WeatherService weatherService;
    @GetMapping("/rs/bestweather")
    public ResponseEntity<Weather> getBestWeather() {
        return ResponseEntity.accepted().body(weatherService.getBestWeather());
    }
}
