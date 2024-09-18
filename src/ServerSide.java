import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerSide {
    private static final int PORT = 1234;

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server is listening on port " + PORT);
            UserService service = new UserService();
            while (true) {
                try (Socket clientSocket = serverSocket.accept();
                     PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                     BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {

                    System.out.println("Client connected");

                    while (true) {
                        displayMenu(out);
                        String input = in.readLine();
                        if (input == null) {
                            System.out.println("Client disconnected");
                            break;
                        }

                        int choice = Integer.parseInt(input);
                        switch (choice) {
                            case 0:
                                out.println("Exiting...");
                                out.println();
                                return;
                            case 1:
                                out.println("Please input an id. (Long) ");
                                String idInput = in.readLine();
                                if (idInput == null) {
                                    System.out.println("Client disconnected");
                                    return;
                                } else {
                                    Long id = Long.parseLong(idInput);
                                    out.println("Received ID: " + id);
                                    out.println(service.findById(id).toString());
                                }
                                break;
                            case 3:
                                out.println("Please input an id to delete. (Long) ");
                                Long id = Long.parseLong(in.readLine());
                                if (service.findById(id) == null) {
                                    System.out.println("ID is not found");
                                    out.println("Your id is not valid ");
                                } else {
                                    service.deleteUserBydId(id);
                                    out.println("User deleted successfully");
                                }
                                break;
                            case 2:
                                out.println("Please enter user details.");
                                out.println("ID: ");
                                Long uid = Long.parseLong(in.readLine());
                                if (service.findById(uid) != null) {
                                    out.println("UserID already exists please input another id");
                                    break;
                                }
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
                                var respone = service.addUser(user);
                                out.println(respone.toString());
                                break;
                            case 4:
                                out.println("Update user functionality not implemented yet.");
                                out.println();
                                break;
                            case 5:
                                out.println("List all users functionality not implemented yet.");
                                out.println();
                                break;
                            default:
                                out.println("Invalid option. Please try again.");
                                out.println();
                        }
                    }
                } catch (Exception e) {
                    System.err.println("Error handling client: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            System.err.println("Could not listen on port " + PORT);
            e.printStackTrace();
        }
    }

    private static void displayMenu(PrintWriter out) {
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
