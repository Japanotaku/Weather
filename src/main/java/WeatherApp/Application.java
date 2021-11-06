package WeatherApp;


import java.io.IOException;
import java.sql.SQLException;

public class Application {

    public static void main(String[] args) throws SQLException, IOException {
        UserInterfaceDB userInterface = new UserInterfaceDB();
        userInterface.runApplication();

    }
}
