import com.mysql.cj.jdbc.CallableStatement;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

public class UserService implements UserServiceInterface {
    private static final String URL = "jdbc:mysql://localhost/javamysql";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "160304";
    private Connection getConnection() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }

    @Override
    public User findById(Long id) {
        String sql = "SELECT * FROM user WHERE id = ?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return extractUser(resultSet); // Extract User via helper
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            handleException(e);
        }
        return null;
    }

    @Override
    public void deleteUserBydId(Long id) {
        String sql = "DELETE FROM user WHERE id = ?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("User with ID " + id + " deleted successfully.");
            } else {
                System.out.println("No user found with ID " + id);
            }
        } catch (SQLException | ClassNotFoundException e) {
            handleException(e);
        }
    }

    @Override
    public List<User> findAll() {
        String sql = "SELECT * FROM user";
        List<User> users = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                users.add(extractUser(resultSet));
            }
        } catch (SQLException | ClassNotFoundException e) {
            handleException(e);
        }
        return users;
    }

    @Override
    public User addUser(User user) {
        String sql = "INSERT INTO user (id, firstName, lastName, address, age, email) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, user.getId());
            statement.setString(2, user.getFirstName());
            statement.setString(3, user.getLastName());
            statement.setString(4, user.getAddress());
            statement.setLong(5, user.getAge());
            statement.setString(6, user.getEmail());
            statement.executeUpdate();  // Using executeUpdate for insert

            return user;
        } catch (SQLException | ClassNotFoundException e) {
            handleException(e);
            return null;
        }
    }

    @Override
    public User updateById(Long id , User user) {
        String sql  = "UPDATE user SET firstName = ?, lastName = ?, age = ?, email = ? WHERE id = ?";
        try(Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
        ) {
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setLong(3, user.getAge());
            statement.setString(4, user.getEmail());
            statement.setLong(5, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    // Helper method to extract a User object from ResultSet
    private User extractUser(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getLong("id"));
        user.setFirstName(resultSet.getString("firstName"));
        user.setLastName(resultSet.getString("lastName"));
        user.setAddress(resultSet.getString("address"));
        user.setAge(resultSet.getLong("age"));
        user.setEmail(resultSet.getString("email"));
        return user;
    }

    // General exception handler
    private void handleException(Exception e) {
        System.err.println("Database error: " + e.getMessage());
        e.printStackTrace();
    }
    public User catchUser(PrintWriter out , BufferedReader in , Long uid ) throws IOException {
        out.println("Firstname: ");
        String firstName = in.readLine();
        out.println("Lastname: ");
        String lastName = in.readLine();
        out.println("Address: ");
        String address = in.readLine();
        out.println("Age: ");
        Long age = Long.parseLong(in.readLine());
        out.println("Email: ");
        String email = in.readLine();
        User user = new User(address, age, firstName, lastName, uid, email);
        return user;
    }
}
