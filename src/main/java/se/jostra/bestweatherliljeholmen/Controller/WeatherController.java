package se.jostra.bestweatherliljeholmen.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import se.jostra.bestweatherliljeholmen.data.Weather;
import se.jostra.bestweatherliljeholmen.service.WeatherService;

@Controller
public class WeatherController {
    @Autowired
    WeatherService weatherService;

    @GetMapping("/")
    public String getBestWeather(Model model) {
        Weather weather = weatherService.getBestWeather();

        model.addAttribute("timestamp", weather.getLocalDateTime());
        model.addAttribute("celsius", weather.getCelsius());
        model.addAttribute("windspeed", weather.getWindSpeed());
        model.addAttribute("winddirection",
                weatherService.degreesToCardinalDirection(weather.getWindFromDirection()));
        model.addAttribute("cloudchance", weather.getCloudChance());
        model.addAttribute("rain", weather.getRainAmount());
        model.addAttribute("humidity", weather.getRelativeHumidity());
        model.addAttribute("source", weather.getWeatherSource());
        return "index";
    }
}
