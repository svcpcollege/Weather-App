import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import org.json.JSONObject;

public class OpenWeatherMapAPI{
    private final String API_KEY;
    private final String API_URL;
    private final JSONObject weatherData;

    /**
     * Constructs an API object for the specified city.
     *
     * @param city the city name
     * @throws IOException if an I/O error occurs
     */
    public OpenWeatherMapAPI(String city) throws IOException{
        API_KEY = "6bacb232a0f59d0ec64765b01657a03e"; // Get your API key from https://openweathermap.org/api
        API_URL = "https://api.openweathermap.org/data/2.5/weather?q="+city+"&appid="+API_KEY+"&units=metric";
        
        URL weatherUrl = new URL(API_URL);
        HttpURLConnection connection = (HttpURLConnection) weatherUrl.openConnection();
        connection.setRequestMethod("GET");

        if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
            throw new RuntimeException("Failed : HTTP error code : "
                    + connection.getResponseCode());
        }

        try (Scanner urlScanner = new Scanner(connection.getInputStream())) {
            weatherData = new JSONObject(urlScanner.nextLine());
        }
    }


    public double getTemperature(){
        return weatherData.getJSONObject("main").getDouble("temp");
    }


    public double getMinTemperature(){
        return weatherData.getJSONObject("main").getDouble("temp_min");
    }


    public double getMaxTemperature(){
        return weatherData.getJSONObject("main").getDouble("temp_max");
    }


    public String getWeatherCondition(){
        return weatherData.getJSONArray("weather").getJSONObject(0).getString("main");
    }


    public double getHumidity(){
        return weatherData.getJSONObject("main").getDouble("humidity");
    }


    public double getWindSpeed(){
        return weatherData.getJSONObject("wind").getDouble("speed");
    }


    public double getWindDirection(){
        return weatherData.getJSONObject("wind").getDouble("deg");
    }


    public double getVisibility(){
        return weatherData.getDouble("visibility");
    }
}


