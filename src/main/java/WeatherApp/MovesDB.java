package WeatherApp;

import WeatherApp.entity.WeatherData;

import java.io.IOException;
import java.sql.*;
import java.util.Scanner;

public class MovesDB {
    private static Connection connection;
    private static Scanner read = new Scanner(System.in);
    private static Statement statement;
    private static PreparedStatement preparedStatement;
    private static ControllerDB controller = new ControllerDB();

    public static void DoDBMoves() throws SQLException {
        try{
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:lesson8db.db");
            statement = connection.createStatement();
            System.out.println("connected");
            performCreateWeatherDB();
            performPreparedWeatherStatement();
            readWeatherFromDB();


        } catch (ClassNotFoundException | SQLException | IOException e) {
            e.printStackTrace();
        }finally{
            connection.close();
            statement.close();
            preparedStatement.close();
        }
    }
    private static void performDropTable() throws SQLException {
        statement.executeUpdate("DROP TABLE IF EXISTS weather");
    }
    private static void performCreateWeatherDB() throws SQLException {
        statement.executeUpdate("CREATE TABLE IF NOT EXISTS weather (id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "city STRING, localTime STRING,weatherText String,temperature DOUBLE);");
    }
    private static void performPreparedWeatherStatement() throws SQLException, IOException {
        WeatherData weather = controller.getCurrentWeather();
        // Демонстрация prepared statement
        preparedStatement = connection.prepareStatement("INSERT INTO weather (city,localTime,weatherText,temperature) VALUES (?,?,?,?)");
        for (int i = 1; i < 2; i++) {
            preparedStatement.setString(1, weather.getCity());
            preparedStatement.setString(2, weather.getLocalDate());
            preparedStatement.setString(3, weather.getText());
            preparedStatement.setDouble(4,weather.getTemperature());
//                preparedStatement.execute();
            preparedStatement.addBatch();
        }
        preparedStatement.executeBatch();
    }

    private static void readWeatherFromDB() throws SQLException {
        ResultSet resultSet = statement.executeQuery("SELECT * FROM weather");
        // В данном случае result set выгружает всю результирующую выборку
        while (resultSet.next()) {
            System.out.println(
                    resultSet.getInt(1) + " | " +
                            resultSet.getString(2) + " | " +
                            resultSet.getString("localTime") + " | " +
                            resultSet.getString("weatherText") + " | " +
                            resultSet.getDouble("temperature") + " | "
            );
        }
    }
}
