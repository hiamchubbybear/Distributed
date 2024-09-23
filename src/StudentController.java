import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Scanner;

public class StudentController {
    private StudentService service = new StudentService();
    public void getById(BufferedReader in, PrintWriter out) throws IOException {
        out.println("Please input an id. (Long) ");
        String idInput = in.readLine();
        if (idInput == null) {
            System.out.println("Client disconnected");
            return;
        } else {
            Long id = Long.parseLong(idInput);
            out.println("Received ID: " + id);
            var data = service.findById(id);
            if (data != null)
                out.println(data.toString());
            else out.println("ID is invalid");
        }
    }

    public void deleteById(BufferedReader in, PrintWriter out) throws IOException {
        out.println("Please input an id to delete. (Long) ");
        Long id = Long.parseLong(in.readLine());
        if (service.findById(id) == null) {
            System.out.println("ID is not found");
            out.println("Your id is not valid ");
        } else {
            service.deleteUserBydId(id);
            out.println("User deleted successfully");
        }
    }

    public void addUser(BufferedReader in, PrintWriter out) throws IOException {
        out.println("Please enter user details.");
        out.println("ID: ");
        Long uid = Long.parseLong(in.readLine());
        if (service.findById(uid) != null) {
            out.println("UserID already exists please input another id");
            return;
        }
        Student student = service.catchUser(out, in, uid);
        var respone = service.addUser(student);
        out.println(respone.toString());
    }

    public void updateStudent(PrintWriter out, BufferedReader in) throws IOException {
        out.println("Please enter user details.");
        out.println("ID: ");
        Long uid = Long.parseLong(in.readLine());
        Student student = service.findById(uid);
        Student student1 = inStudent(in, out);
        if (student == null) {
            System.out.println("ID is not found");
            return;
        } else {
            out.println(service.updateById(uid, student1));
        }
    }

    public void findByName(PrintWriter out, BufferedReader in) throws IOException {
        out.println("Please enter student first name.");
        String firstName = in.readLine();
//        out.println("Please enter student last name.");
        String lastName = in.readLine();
        service.findByName(firstName, lastName).forEach(x -> out.println(x.toString()));
    }

    public void listALl(BufferedReader in, PrintWriter out) throws IOException {
        out.println("List all user in database.");
        List<Student> students = service.findAll();
        students.forEach(item -> out.println(item.toString()));
    }

    public Student inStudent(BufferedReader in, PrintWriter out) throws IOException {
        Student student = new Student();
        out.println("Please input student ID.");
        student.setId(Long.parseLong(in.readLine()));
        out.println("Please input student first name.");
        student.setFirstName(in.readLine());
        out.println("Please input student last name.");
        student.setLastName(in.readLine());
        out.println("Please input student email.");
        student.setAge(Long.parseLong(in.readLine()));
        out.println("Please input student phone number.");
        student.setEmail(in.readLine());
        return student;

    }

    public void displayMenu(PrintWriter out) {
        out.println("Please choose an option (number):");
        out.println("0. Exit");
        out.println("1. Find by ID");
        out.println("2. Insert a user");
        out.println("3. Delete a user");
        out.println("4. Update a user");
        out.println("5. List all users");
        out.println("6. Find by first name and last name");
        out.println();
    }
}
