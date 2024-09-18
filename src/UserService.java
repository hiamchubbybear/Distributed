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

    public User findById(Long id) {
        String sql = "SELECT * FROM user WHERE id = ?";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    User user = new User();
                    user.setAddress(resultSet.getString("address"));
                    user.setAge(resultSet.getLong("age"));
                    user.setEmail(resultSet.getString("email"));
                    user.setFirstName(resultSet.getString("firstName"));
                    user.setId(resultSet.getLong("id"));
                    user.setLastName(resultSet.getString("lastName"));
                    return user;
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
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
            e.printStackTrace();
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
                User user = new User();
                user.setAddress(resultSet.getString("address"));
                user.setAge(resultSet.getLong("age"));
                user.setEmail(resultSet.getString("email"));
                user.setFirstName(resultSet.getString("firstName"));
                user.setId(resultSet.getLong("id"));
                user.setLastName(resultSet.getString("lastName"));
                users.add(user);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public User updateById(Long id) {
        // Implementation depends on what fields you want to update
        // This is a placeholder implementation
        throw new UnsupportedOperationException("Method 'updateById' needs to be implemented");
    }
}