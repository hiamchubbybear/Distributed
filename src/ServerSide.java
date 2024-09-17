import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerSide {
    private static final int PORT = 1234;

    public static void main(String[] args) {
        // UserController controller = new UserController();
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server is listening on port " + PORT);

            while (true) {
                try (Socket clientSocket = serverSocket.accept();
                        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {

                    System.out.println("New client connected");
                    while ((in.ready())) {
                        int choice = Integer.parseInt(in.readLine());
                        switch (choice) {
                            case 0:
                                out.println("Exiting...");
                                return;
                            case 1:
                                out.println("Please input an id:");
                                Long id = Long.parseLong(in.readLine());
                                // out.println(controller.getById(id).toString());

                                break;
                            case 2:
                                out.println("Insert user functionality not implemented yet.");
                                break;
                            case 3:
                                out.println("Delete user functionality not implemented yet.");
                                break;
                            case 4:
                                out.println("Update user functionality not implemented yet.");
                                break;
                            case 5:
                                out.println("List all users functionality not implemented yet.");
                                break;
                            default:
                                out.println("Invalid option. Please try again.");
                        }

                        displayMenu(out);
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
        out.println("2. Insert a user");
        out.println("3. Delete a user");
        out.println("4. Update a user");
        out.println("5. List all users");
    }

}
