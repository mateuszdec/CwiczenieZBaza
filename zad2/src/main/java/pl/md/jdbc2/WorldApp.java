package pl.md.jdbc2;

import java.sql.*;
import java.util.Scanner;

public class WorldApp {

    private Connection connection;

    public WorldApp() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Błąd wczytywania sterownika");
        }
        String url = "jdbc:mysql://localhost:3306/world?serverTimezone=UTC";

        try {
            connection = DriverManager.getConnection(url, "root", "wpiszhaslo1");
        } catch (SQLException throwables) {
            System.out.println("Błąd nawiązywania połączenia!");
            throwables.printStackTrace();
        }
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Podaj nazwę miasta");
        String cityName = scanner.nextLine();
        System.out.println("Podaj nową liczbę ludności");
        int newPopulation = scanner.nextInt();
        scanner.nextLine();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE city SET Population = ? WHERE Name = ?");
            preparedStatement.setInt(1, newPopulation);
            preparedStatement.setString(2, cityName);
            int rowsChanged = preparedStatement.executeUpdate();

            System.out.println("Zaktualizowane rekordy: " + rowsChanged);

            PreparedStatement queryStatement = connection.prepareStatement("SELECT * FROM City WHERE Name = ?");
            queryStatement.setString(1, cityName);
            ResultSet resultSet = queryStatement.executeQuery();
            if (resultSet.next()) {
                long id = resultSet.getLong("ID");
                String name = resultSet.getString("Name");
                int population = resultSet.getInt("Population");
                System.out.println("Id: " + id + " " + name + " ludność: " + population);
            }


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        try {
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
