package top.szzz666.LobbyManage.tools;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import javax.naming.ConfigurationException;
import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.net.ProtocolException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Scanner;
import java.util.TimeZone;

public class SunriseSunsetRequestObject {
    private String sunriseTime;
    private String sunsetTime;

    public SunriseSunsetRequestObject(TimeZone timeZone, String lat, String lon) throws IOException, ConfigurationException {
        URL url = new URL("https://api.sunrisesunset.io/json?lat="+lat+"&lng="+lon+"&timezone=UTC");
        HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.connect();
        int responseCode = con.getResponseCode();
        if (responseCode >= 400) {
            throw new ProtocolException("Server/client error (HTTP error " + responseCode + ")");
        } else {
            Scanner scanner = new Scanner(url.openStream());
            StringBuilder data = new StringBuilder();

            while (scanner.hasNext()) {
                data.append(scanner.nextLine());
            }

            scanner.close();
            JsonObject jsonResponse = JsonParser.parseString(data.toString()).getAsJsonObject();
            JsonObject results = jsonResponse.getAsJsonObject("results");

            if (results == null) {
                throw new ConfigurationException("No results found in API response.");
            }

            this.sunriseTime = results.get("sunrise").getAsString();
            this.sunsetTime = results.get("sunset").getAsString();
            if (!this.sunriseTime.equalsIgnoreCase("null") && !this.sunsetTime.equalsIgnoreCase("null")) {
                DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("h:mm:ss a", Locale.ENGLISH);
                LocalDate currentDate = LocalDate.now(ZoneId.of("UTC"));
                this.sunriseTime = ZonedDateTime.of(currentDate, LocalTime.parse(this.sunriseTime, timeFormatter), ZoneId.of("UTC")).withZoneSameInstant(timeZone.toZoneId()).format(timeFormatter);
                this.sunsetTime = ZonedDateTime.of(currentDate, LocalTime.parse(this.sunsetTime, timeFormatter), ZoneId.of("UTC")).withZoneSameInstant(timeZone.toZoneId()).format(timeFormatter);
            } else {
                throw new ConfigurationException("Time(s) returned null. Check the sunrise/sunset longitude and latitude.");
            }
        }
    }

    public String getSunriseTime() {
        return this.sunriseTime;
    }

    public String getSunsetTime() {
        return this.sunsetTime;
    }
}
