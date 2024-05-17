
package se.jostra.bestweatherliljeholmen.data.meteo;

import java.util.LinkedHashMap;
import java.util.Map;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "time",
    "temperature_2m",
    "relative_humidity_2m",
    "precipitation",
    "pressure_msl",
    "cloud_cover",
    "wind_speed_10m",
    "wind_direction_10m"
})

public class HourlyUnits {

    @JsonProperty("time")
    private String time;
    @JsonProperty("temperature_2m")
    private String temperature2m;
    @JsonProperty("relative_humidity_2m")
    private String relativeHumidity2m;
    @JsonProperty("precipitation")
    private String precipitation;
    @JsonProperty("pressure_msl")
    private String pressureMsl;
    @JsonProperty("cloud_cover")
    private String cloudCover;
    @JsonProperty("wind_speed_10m")
    private String windSpeed10m;
    @JsonProperty("wind_direction_10m")
    private String windDirection10m;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();

    @JsonProperty("time")
    public String getTime() {
        return time;
    }

    @JsonProperty("time")
    public void setTime(String time) {
        this.time = time;
    }

    @JsonProperty("temperature_2m")
    public String getTemperature2m() {
        return temperature2m;
    }

    @JsonProperty("temperature_2m")
    public void setTemperature2m(String temperature2m) {
        this.temperature2m = temperature2m;
    }

    @JsonProperty("relative_humidity_2m")
    public String getRelativeHumidity2m() {
        return relativeHumidity2m;
    }

    @JsonProperty("relative_humidity_2m")
    public void setRelativeHumidity2m(String relativeHumidity2m) {
        this.relativeHumidity2m = relativeHumidity2m;
    }

    @JsonProperty("precipitation")
    public String getPrecipitation() {
        return precipitation;
    }

    @JsonProperty("precipitation")
    public void setPrecipitation(String precipitation) {
        this.precipitation = precipitation;
    }

    @JsonProperty("pressure_msl")
    public String getPressureMsl() {
        return pressureMsl;
    }

    @JsonProperty("pressure_msl")
    public void setPressureMsl(String pressureMsl) {
        this.pressureMsl = pressureMsl;
    }

    @JsonProperty("cloud_cover")
    public String getCloudCover() {
        return cloudCover;
    }

    @JsonProperty("cloud_cover")
    public void setCloudCover(String cloudCover) {
        this.cloudCover = cloudCover;
    }

    @JsonProperty("wind_speed_10m")
    public String getWindSpeed10m() {
        return windSpeed10m;
    }

    @JsonProperty("wind_speed_10m")
    public void setWindSpeed10m(String windSpeed10m) {
        this.windSpeed10m = windSpeed10m;
    }

    @JsonProperty("wind_direction_10m")
    public String getWindDirection10m() {
        return windDirection10m;
    }

    @JsonProperty("wind_direction_10m")
    public void setWindDirection10m(String windDirection10m) {
        this.windDirection10m = windDirection10m;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(HourlyUnits.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("time");
        sb.append('=');
        sb.append(((this.time == null)?"<null>":this.time));
        sb.append(',');
        sb.append("temperature2m");
        sb.append('=');
        sb.append(((this.temperature2m == null)?"<null>":this.temperature2m));
        sb.append(',');
        sb.append("relativeHumidity2m");
        sb.append('=');
        sb.append(((this.relativeHumidity2m == null)?"<null>":this.relativeHumidity2m));
        sb.append(',');
        sb.append("precipitation");
        sb.append('=');
        sb.append(((this.precipitation == null)?"<null>":this.precipitation));
        sb.append(',');
        sb.append("pressureMsl");
        sb.append('=');
        sb.append(((this.pressureMsl == null)?"<null>":this.pressureMsl));
        sb.append(',');
        sb.append("cloudCover");
        sb.append('=');
        sb.append(((this.cloudCover == null)?"<null>":this.cloudCover));
        sb.append(',');
        sb.append("windSpeed10m");
        sb.append('=');
        sb.append(((this.windSpeed10m == null)?"<null>":this.windSpeed10m));
        sb.append(',');
        sb.append("windDirection10m");
        sb.append('=');
        sb.append(((this.windDirection10m == null)?"<null>":this.windDirection10m));
        sb.append(',');
        sb.append("additionalProperties");
        sb.append('=');
        sb.append(((this.additionalProperties == null)?"<null>":this.additionalProperties));
        sb.append(',');
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

}
