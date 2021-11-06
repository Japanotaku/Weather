package WeatherApp;

import WeatherApp.entity.WeatherData;

import java.io.IOException;
import java.sql.*;
import java.util.List;

public class DatabaseRepositorySQLiteImpl implements DatabaseRepository {
    private static Statement statement;
    static {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    String filename = "application.db";
    String createTableQuery = "CREATE TABLE IF NOT EXISTS weather (\n" +
        "id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
        "city TEXT NOT NULL,\n" +
        "date_time TEXT NOT NULL,\n" +
        "weather_text TEXT NOT NULL,\n" +
        "temperature REAL NOT NULL,\n" +
        ");";
    String insertWeatherQuery = "INSERT INTO weather (city, date_time, weather_text, temperature) VALUES (?,?,?,?)";

    public DatabaseRepositorySQLiteImpl() {
        filename = ApplicationGlobalState.getInstance().getDbFileName();
    }

    private Connection getConnection() throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:sqlite:" + filename);
        return connection;
    }

    private void createTableIfNotExists() {
        try (Connection connection = getConnection()) {
            connection.createStatement().execute(createTableQuery);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public boolean saveWeatherData(WeatherData weatherData) throws SQLException {
        try (Connection connection = getConnection();
        PreparedStatement saveWeather = connection.prepareStatement(insertWeatherQuery)) {
            saveWeather.setString(1, weatherData.getCity());
            saveWeather.setString(2, weatherData.getLocalDate());
            saveWeather.setString(3, weatherData.getText());
            saveWeather.setDouble(4, weatherData.getTemperature());
            return saveWeather.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        throw new SQLException("Failure on saving weather object");
    }

    @Override
    public List<WeatherData> getAllSavedData() throws IOException {
        throw new IOException("Not implemented exception");

    }
    private void readDataFromDB() throws SQLException {
        ResultSet resultSet = statement.executeQuery("SELECT * FROM weather");
        // В данном случае result set выгружает всю результирующую выборку
        while (resultSet.next()) {
            System.out.println(
                    resultSet.getString(1) + " | " +
                            resultSet.getString(2) + " | " +
                            resultSet.getString(3) + " | " +
                            resultSet.getDouble(4) + " | "
            );
        }
        statement.close();
    }

}
