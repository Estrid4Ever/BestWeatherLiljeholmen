package se.jostra.bestweatherliljeholmen.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.jostra.bestweatherliljeholmen.data.Weather;
import se.jostra.bestweatherliljeholmen.data.WeatherDAO;

import java.util.Comparator;
import java.util.List;

@Service
public class WeatherService {
    @Autowired
    WeatherDAO weatherDAO;

    public Weather getBestWeather() {
        List<Weather> allWeather = weatherDAO.getAllWeatherForecasts();

        allWeather.sort(Comparator
                .comparingDouble(Weather::getRainAmount)
                .thenComparingDouble(Weather::getCelsius).reversed()
                .thenComparingDouble(Weather::getCloudChance).reversed()
        );

        for (int i = 0; i < allWeather.size(); i++) {
            System.out.println(allWeather.get(i));
        }

        return allWeather.getLast();
    }

    public String degreesToCardinalDirection(int degrees) {
        if (degrees >= 23 && degrees < 68) {
            return "nordöst";
        }
        if (degrees >= 68 && degrees < 113) {
            return "öst";
        }
        if (degrees >= 113 && degrees < 158) {
            return "sydöst";
        }
        if (degrees >= 158 && degrees < 203) {
            return "syd";
        }
        if (degrees >= 203 && degrees < 248) {
            return "sydväst";
        }
        if (degrees >= 248 && degrees < 293) {
            return "väst";
        }
        if (degrees >= 293 && degrees < 338) {
            return "nordväst";
        }
        return "nord";
    }
}
