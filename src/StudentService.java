import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentService implements StudentServiceInterface {
    private static final String URL = "jdbc:mysql://localhost/javamysql";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "160304";

    private Connection getConnection() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        System.out.println("Connected to database");
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }

    @Override
    public Student findById(Long id) {
        String sql = "SELECT * FROM student WHERE id = ?";
        try (Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return extractUser(resultSet);
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            handleException(e);
        }
        return null;
    }

    @Override
    public List<Student> findByName(String firstname , String lastname) {
        String sql = "SELECT * FROM student WHERE firstname = ? and lastname = ?";
        List<Student> students = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, firstname);
            statement.setString(2, lastname);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    System.out.println(extractUser(resultSet).toString());
                     students.add(extractUser(resultSet));
                }
                return students;
            }
        } catch (SQLException | ClassNotFoundException e) {
            handleException(e);
        }
        return null;
    }

    @Override
    public void deleteUserBydId(Long id) {
        String sql = "DELETE FROM student WHERE id = ?";
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
    public List<Student> findAll() {
        String sql = "SELECT * FROM student";
        List<Student> students = new ArrayList<>();
        try (Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                System.out.println(extractUser(resultSet).toString());
                students.add(extractUser(resultSet));
            }
        } catch (SQLException | ClassNotFoundException e) {
            handleException(e);
        }
        return students;
    }

    @Override
    public Student addUser(Student student) {
        String sql = "INSERT INTO student (id, firstName, lastName, address, age, email) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, student.getId());
            statement.setString(2, student.getFirstName());
            statement.setString(3, student.getLastName());
            statement.setString(4, student.getAddress());
            statement.setLong(5, student.getAge());
            statement.setString(6, student.getEmail());
            statement.executeUpdate();
            return student;
        } catch (SQLException | ClassNotFoundException e) {
            handleException(e);
            return null;
        }
    }

    @Override
    public Student updateById(Long id, Student student) {
        String sql = "UPDATE student SET firstName = ?, lastName = ?, age = ?, email = ? WHERE id = ?";
        try (Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);) {
            statement.setString(1, student.getFirstName());
            statement.setString(2, student.getLastName());
            statement.setLong(3, student.getAge());
            statement.setString(4, student.getEmail());
            statement.setLong(5, id);
            statement.executeUpdate();
            return student;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    private Student extractUser(ResultSet resultSet) throws SQLException {
        Student student = new Student();
        student.setId(resultSet.getLong("id"));
        student.setFirstName(resultSet.getString("firstname"));
        student.setLastName(resultSet.getString("lastname"));
        student.setAddress(resultSet.getString("address"));
        student.setAge(resultSet.getLong("age"));
        student.setEmail(resultSet.getString("email"));
        return student;
    }

    private void handleException(Exception e) {
        System.err.println("Database error: " + e.getMessage());
        e.printStackTrace();
    }

    public Student catchUser(PrintWriter out, BufferedReader in, Long uid) throws IOException {
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
        Student student = new Student(uid,firstName,lastName,address,age,email);
        return student;
    }
}
