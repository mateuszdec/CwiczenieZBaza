import java.sql.*;
import java.util.Scanner;

public class WorldApp {

    private Connection connection;
    private Scanner scanner;

    public void run() {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Sterownik do bazy nie został znaleziony");
            return;
        }

        String url = "jdbc:mysql://localhost:3306/world?serverTimezone=UTC";
        try {
            connection = DriverManager.getConnection(url, "root", "wpiszhaslo1");
        } catch (SQLException e) {
            System.out.println("Błąd podczas nawiązywania połączenia: " + e.getMessage());
        }

        while (true) {
            System.out.println("Wybierz opje");
            System.out.println("1. Miasta z Polski");
            System.out.println("2. Miasta dla kodu kraju");
            System.out.println("3. Informacja o języku");
            System.out.println("0. Koniec");
            scanner = new Scanner(System.in);
            String option = scanner.nextLine();

            switch (option) {
                case "1":
                    citiesForPoland();
                    break;
                case "2":
                    citiesForGivenCountryCode();
                    break;
                case "3":
                    languageInfo();
                    break;
                case "0":
                    close();
                    return;

                default:
                    System.out.println("Nieznana opcja!");
            }
        }
    }

    private void languageInfo() {

        System.out.println("Podaj język. Do wyboru masz: ");
        displayAllLanguages();
        String language = scanner.nextLine();

        String sql = "SELECT Name, IsOfficial, Percentage FROM countrylanguage cl " +
                "JOIN country c ON c.code = cl.CountryCode " +
                "WHERE Language = ? " +
                "ORDER BY Percentage DESC";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, language);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String countryName = resultSet.getString("Name");
                String isOfficial = resultSet.getString("IsOfficial");
                double percentage = resultSet.getDouble("Percentage");

                String offical = (isOfficial.equals(("T")) ? "Oficialny" : "Nieoficjalny");
                System.out.printf("%-40s %-10s %f%n", countryName, offical, percentage);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void displayAllLanguages() {

        try {
            String sql = "SELECT Distinct Language FROM countrylanguage";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String language = resultSet.getString("Language");
                System.out.println(language);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            System.out.println("Błąd podczas zamykania połączenia: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void citiesForPoland() {
        featchAndDisplayCities("POL");
    }


    private void citiesForGivenCountryCode() {
        System.out.println("Podaj kod kraju");
        String countryCode = scanner.nextLine();
        featchAndDisplayCities(countryCode);
    }

    private void featchAndDisplayCities(String countryCode) {
        try {
            String sql = "SELECT * FROM city WHERE CountryCode = ? ORDER BY Name ASC ";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, countryCode);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String cityName = resultSet.getString("Name");
                int population = resultSet.getInt("Population");
                System.out.println(cityName + " Ludność: " + population);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
