import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class UserController {
    private UserService service = new UserService();
    public void getById(BufferedReader in , PrintWriter out) throws IOException {
        out.println("Please input an id. (Long) ");
        String idInput = in.readLine();
        if (idInput == null) {
            System.out.println("Client disconnected");
            return ;
        } else {
            Long id = Long.parseLong(idInput);
            out.println("Received ID: " + id);
            var data =service.findById(id);
            if(data != null)
            out.println(data.toString());
            else out.println("ID is invalid");
        }
    }
    public void deleteById(BufferedReader in , PrintWriter out) throws IOException {
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
    public void addUser(BufferedReader in , PrintWriter out) throws IOException {
        out.println("Please enter user details.");
        out.println("ID: ");
        Long uid = Long.parseLong(in.readLine());
        if (service.findById(uid) != null) {
            out.println("UserID already exists please input another id");
            return;
        }
        User user = service.catchUser(out,in,uid);
        var respone = service.addUser(user);
        out.println(respone.toString());
    }
    public void listALl(BufferedReader in , PrintWriter out) throws IOException {
        out.println("List all user in database.");
        List<User> users = service.findAll();
        users.forEach(item -> out.println(item.toString()));
    }
    public void displayMenu(PrintWriter out) {
        out.println("Please choose an option (number):");
        out.println("0. Exit");
        out.println("1. Find by ID");
        out.println("2. Delete a user");
        out.println("3. Insert a user");
        out.println("4. Update a user");
        out.println("5. List all users");
        out.println();
    }
}
