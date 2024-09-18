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

    // Common method to get a database connection
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
            return null; // If exception occurs, return null (or throw a custom exception)
        }
    }

    @Override
    public User updateById(Long id) {
        // This method would depend on the specific fields being updated, implementation can be added later
        throw new UnsupportedOperationException("Method 'updateById' needs to be implemented.");
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
        // Logging the error (could use a logger in a real-world application)
        System.err.println("Database error: " + e.getMessage());
        e.printStackTrace();
    }
}
