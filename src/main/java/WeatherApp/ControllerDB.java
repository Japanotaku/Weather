package WeatherApp;



import WeatherApp.entity.WeatherData;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;

import java.util.Map;
import java.util.Scanner;

public class ControllerDB {
    private static Scanner reader = new Scanner(System.in);
    WeatherProvider weatherProvider = new AccuWeathProvider();
    Map<Integer, Functionality> variantResult = new HashMap();

    public ControllerDB() {
        variantResult.put(1, Functionality.GET_CURRENT_WEATHER);
        variantResult.put(2, Functionality.GET_WEATHER_IN_NEXT_5_DAYS);
        variantResult.put(3, Functionality.DELETE_DB);
    }

    public void onUserInput(String input) throws IOException, SQLException {
        int command = Integer.parseInt(input);
        if (!variantResult.containsKey(command)) {
            throw new IOException("There is no command for command-key " + command);
        }

        switch (variantResult.get(command)) {
            case GET_CURRENT_WEATHER -> ggg();
            case GET_WEATHER_IN_NEXT_5_DAYS -> getWeatherIn5Days();
        }
    }

    public WeatherData getCurrentWeather() throws IOException {
        return weatherProvider.getWeather(Periods.NOW);
    }
    public void ggg() throws SQLException {
        MovesDB.DoDBMoves();
    }


    public void getWeatherIn5Days() throws IOException {
        weatherProvider.getWeather(Periods.FIVE_DAYS);
    }
}
