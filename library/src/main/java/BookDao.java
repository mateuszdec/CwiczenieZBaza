import java.sql.*;

public class BookDao {
    public void inset(Book book) {
        Connection connection = connect();

        PreparedStatement preparedStatement = null;
        try {
            String sql = "INSERT INTO books(title, author, year, isbn) VALUES (?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setString(2, book.getAuthor());
            preparedStatement.setInt(3, book.getYear());
            preparedStatement.setString(4, book.getIsbn());
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            System.out.println("Niepowodzenie podczas zapisu do bazy: " + throwables.getMessage());
        }
        closeConnection(connection);
    }

    public Book findByIsbn(String isbn) {
        Connection connection = connect();

        PreparedStatement preparedStatement = null;
        try {
            String sql = "SELECT * FROM books WHERE isbn = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, isbn);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                long id = resultSet.getLong("id");
                String title = resultSet.getString("title");
                String author = resultSet.getString("author");
                int year = resultSet.getInt("year");
                String isbnFromDb = resultSet.getString("isbn");
                Book book = new Book(id, title, author, year, isbnFromDb);
                return book;
            }
        } catch (SQLException throwables) {
            System.out.println("Niepowodzenie podczas zapisu do bazy: " + throwables.getMessage());
        }
        closeConnection(connection);
        return null;
    }

    public void update(Book book) {
        Connection connection = connect();

        PreparedStatement preparedStatement = null;
        try {
            String sql = "UPDATE books SET title = ?, author = ?, year = ?, isbn = ? WHERE id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setString(2, book.getAuthor());
            preparedStatement.setInt(3, book.getYear());
            preparedStatement.setString(4, book.getIsbn());
            preparedStatement.setLong(5, book.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            System.out.println("Niepowodzenie podczas zapisu do bazy: " + throwables.getMessage());
        }
        closeConnection(connection);
    }

    public void deleteByIsbn(String isbn) {
        Connection connection = connect();

        PreparedStatement preparedStatement = null;
        try {
            String sql = "DELETE FROM books WHERE isbn = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, isbn);
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            System.out.println("Niepowodzenie podczas usuwania z bazy: " + throwables.getMessage());
        }
        closeConnection(connection);
    }

    private void closeConnection(Connection connection) {
        try {
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private Connection connect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Błąd nawiązania połączenia");
        }
        String url = "jdbc:mysql://localhost:3306/library?serverTimezone=UTC&characterEncoding=utf8";
        try {
            return DriverManager.getConnection(url, "root", "wpiszhaslo1");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

}
