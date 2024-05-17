package se.jostra.bestweatherliljeholmen.data;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import se.jostra.bestweatherliljeholmen.data.met.Met;
import se.jostra.bestweatherliljeholmen.data.meteo.Meteo;
import se.jostra.bestweatherliljeholmen.data.smhi.Smhi;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class WeatherDAO {

    public List<Weather> getAllWeatherForecasts() {
        List<Weather> weathers = Collections.synchronizedList(new ArrayList<>());

        Mono<Smhi> smhiMono = getSmhiWeather();
        Mono<Met> metMono = getMetWeather();
        Mono<Meteo> meteoMono = getMeteoWeather();

        AtomicBoolean smhiIsDone = new AtomicBoolean(false);
        AtomicBoolean metIsDone = new AtomicBoolean(false);
        AtomicBoolean meteoIsDone = new AtomicBoolean(false);

        smhiMono
                .timeout(Duration.ofSeconds(2))
                .subscribe(
                smhi -> {
                    weathers.add(smhiToWeather(smhi));
                },
                error -> {
                    System.out.println("Smhi error");
                },
                () -> {
                    smhiIsDone.set(true);
                }
        );
        metMono
                .timeout(Duration.ofSeconds(2))
                .subscribe(
                met -> {
                    weathers.add(metToWeather(met));
                },
                error -> {
                    System.out.println("Met error");
                },
                () -> {
                    metIsDone.set(true);
                }
        );
        meteoMono
                .timeout(Duration.ofSeconds(2))
                .subscribe(
                meteo -> {
                    weathers.add(meteoToWeather(meteo));
                },
                error -> {
                    System.out.println("Meteo error");
                },
                () -> {
                    meteoIsDone.set(true);
                }
        );

        awaitAllApiCalls(metIsDone, smhiIsDone, meteoIsDone);

        return weathers;
    }

    private Weather meteoToWeather(Meteo meteo) {
        TreeMap<LocalDateTime, Integer> timeHashMap = find24hourFromNowTimeSlot(null, null, meteo);
        int timeSlot = timeHashMap.firstEntry().getValue();
        LocalDateTime localDateTime = timeHashMap.firstEntry().getKey();

        double celsius = meteo.getHourly().getTemperature2m().get(timeSlot);
        double airPressure = meteo.getHourly().getPressureMsl().get(timeSlot);
        int cloudChance = Math.toIntExact(meteo.getHourly().getCloudCover().get(timeSlot));
        int humidity = Math.toIntExact(meteo.getHourly().getRelativeHumidity2m().get(timeSlot));
        double rain = meteo.getHourly().getPrecipitation().get(timeSlot);
        double windSpeed = meteo.getHourly().getWindSpeed10m().get(timeSlot);
        int windDirection = Math.toIntExact(meteo.getHourly().getWindDirection10m().get(timeSlot));


        return new Weather(localDateTime, celsius, airPressure,
                cloudChance, humidity, rain, windDirection, windSpeed, "Meteo");
    }

    private void awaitAllApiCalls(AtomicBoolean metIsDone, AtomicBoolean smhiIsDone, AtomicBoolean meteoIsDone) {
        while (!metIsDone.get() || !smhiIsDone.get() || !meteoIsDone.get()) {
            System.out.println("met: " + metIsDone + " smhi: " + smhiIsDone + " meteo: " + meteoIsDone);
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private Weather smhiToWeather(Smhi smhi) {

        TreeMap<LocalDateTime, Integer> timeHashMap = find24hourFromNowTimeSlot(smhi, null, null);
        int timeSlot = timeHashMap.firstEntry().getValue();
        LocalDateTime localDateTime = timeHashMap.firstEntry().getKey();

        double celsius = 0;
        double airPressure = 0;
        int cloud = 0;
        int humidity = 0;
        double rain = 0;
        double windSpeed = 0;
        int windDirection = 0;

        for (int i = 0; i < smhi.getTimeSeries().get(timeSlot).getParameters().size(); i++) {

            if (smhi.getTimeSeries().get(timeSlot).getParameters().get(i).getName().equals("t")) {
                celsius = smhi.getTimeSeries().get(timeSlot).getParameters().get(i).getValues().get(0);
            }

            if (smhi.getTimeSeries().get(timeSlot).getParameters().get(i).getName().equals("msl")) {
                airPressure = smhi.getTimeSeries().get(timeSlot).getParameters().get(i).getValues().get(0);
            }

            if (smhi.getTimeSeries().get(timeSlot).getParameters().get(i).getName().contains("cc")) {
                cloud += Math.toIntExact(smhi.getTimeSeries().get(timeSlot).getParameters().get(i).getValues().get(0));
            }

            if (smhi.getTimeSeries().get(timeSlot).getParameters().get(i).getName().equals("r")) {
                humidity = Math.toIntExact(smhi.getTimeSeries().get(timeSlot).getParameters().get(i).getValues().get(0));
            }

            if (smhi.getTimeSeries().get(timeSlot).getParameters().get(i).getName().equals("pmin")) {
                rain = smhi.getTimeSeries().get(timeSlot).getParameters().get(i).getValues().get(0);
            }

            if (smhi.getTimeSeries().get(timeSlot).getParameters().get(i).getName().equals("ws")) {
                windSpeed = smhi.getTimeSeries().get(timeSlot).getParameters().get(i).getValues().get(0);
            }

            if (smhi.getTimeSeries().get(timeSlot).getParameters().get(i).getName().equals("wd")) {
                windDirection = Math.toIntExact(smhi.getTimeSeries().get(timeSlot).getParameters().get(i).getValues().get(0));
            }
        }
        int cloudChance = (int) (cloud / 0.32);
        return new Weather(localDateTime, celsius, airPressure,
                cloudChance, humidity, rain, windDirection, windSpeed, "Smhi");
    }

    private TreeMap<LocalDateTime, Integer> find24hourFromNowTimeSlot(Smhi smhi, Met met, Meteo meteo) {

        for (int i = 0; i < getTimeSeriesSize(smhi, met, meteo); i++) {

            String timeStamp = getTimeStamp(smhi, met, meteo, i).substring(0, 16);

            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
            LocalDateTime timeSeries = LocalDateTime.parse(timeStamp, dateTimeFormatter);

            LocalDateTime roundedToClosestPlus24Hour = LocalDateTime.parse(
                    LocalDateTime.now().withMinute(0).withSecond(0)
                            .plusDays(1).toString().substring(0, 16), dateTimeFormatter);


            if (timeSeries.equals(roundedToClosestPlus24Hour)) {
                TreeMap<LocalDateTime, Integer> timeMap = new TreeMap<LocalDateTime, Integer>();
                timeMap.put(timeSeries, i);
                return timeMap;
            }
        }
        TreeMap<LocalDateTime, Integer> timeMap = new TreeMap<LocalDateTime, Integer>();
        timeMap.put(LocalDateTime.now(), 0);
        return timeMap;
    }

    private int getTimeSeriesSize(Smhi smhi, Met met, Meteo meteo) {
        if (smhi != null) {
            return smhi.getTimeSeries().size();
        } else if (met != null) {
            return met.getProperties().getTimeseries().size();
        } else if (meteo != null) {
            return meteo.getHourly().getTime().size();
        } else {
            return 0;
        }
    }

    private String getTimeStamp(Smhi smhi, Met met, Meteo meteo, int index) {
        if (smhi != null) {
            return smhi.getTimeSeries().get(index).getValidTime();
        } else if (met != null) {
            return met.getProperties().getTimeseries().get(index).getTime();
        } else if (meteo != null) {
            return meteo.getHourly().getTime().get(index);
        } else {
            return "0";
        }
    }

    private Mono<Smhi> getSmhiWeather() {
        WebClient client = WebClient.create();

        return client
                .get()
                .uri("https://opendata-download-metfcst.smhi.se/api/category/pmp3g/version/2/geotype/point/lon/18.0300/lat/59.3110/data.json")
                .retrieve()
                .bodyToMono(Smhi.class);
    }

    private Mono<Met> getMetWeather() {
        WebClient client = WebClient.create();

        return client
                .get()
                .uri("https://api.met.no/weatherapi/locationforecast/2.0/compact?lat=59.3110&lon=18.0300")
                .retrieve()
                .bodyToMono(Met.class);
    }

    private Mono<Meteo> getMeteoWeather() {
        WebClient client = WebClient.create();

        return client
                .get()
                .uri("https://api.open-meteo.com/v1/forecast?latitude=59.3094&longitude=18.0234&hourly=temperature_2m," +
                        "relative_humidity_2m,precipitation,pressure_msl,cloud_cover,wind_speed_10m,wind_direction_10m&" +
                        "forecast_days=3")
                .retrieve()
                .bodyToMono(Meteo.class);
    }

    private Weather metToWeather(Met met) {
        TreeMap<LocalDateTime, Integer> timeHashMap = find24hourFromNowTimeSlot(null, met, null);
        int timeSlot = timeHashMap.firstEntry().getValue();
        LocalDateTime localDateTime = timeHashMap.firstEntry().getKey();
        int timeSlotMinusOne = timeSlot == 0 ? 0 : timeSlot -1;

        double celsius = met.getProperties().getTimeseries().get(timeSlot).getData().getInstant().getDetails().getAirTemperature();
        double airPressure = met.getProperties().getTimeseries().get(timeSlot).getData().getInstant().getDetails().getAirPressureAtSeaLevel();
        int cloudChance = (int) Math.round(met.getProperties().getTimeseries().get(timeSlot).getData().getInstant().getDetails().getCloudAreaFraction());
        int humidity = (int) Math.round(met.getProperties().getTimeseries().get(timeSlot).getData().getInstant().getDetails().getRelativeHumidity());
        double rain = met.getProperties().getTimeseries().get(timeSlotMinusOne).getData().getNext1Hours().getDetails().getPrecipitationAmount();
        double windSpeed = (int) Math.round(met.getProperties().getTimeseries().get(timeSlot).getData().getInstant().getDetails().getWindSpeed());
        int windDirection = (int) Math.round(met.getProperties().getTimeseries().get(timeSlot).getData().getInstant().getDetails().getWindFromDirection());


        return new Weather(localDateTime, celsius, airPressure,
                cloudChance, humidity, rain, windDirection, windSpeed, "Met");
    }
}
