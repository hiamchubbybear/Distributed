import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class ServerSide {
    private static final int PORT = 1234;

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server is listening on port " + PORT);
            UserService service = new UserService();
            UserController controller = new UserController();
            while (true) {
                try (Socket clientSocket = serverSocket.accept();
                     PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                     BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {

                    System.out.println("Client connected");

                    while (true) {
                        controller.displayMenu(out);
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
                                controller.getById(in,out);
                                break;
                            case 3:
                                controller.deleteById(in,out);
                                break;
                            case 2:
                                controller.addUser(in,out);
                                break;
                            case 4:
                                out.println("Update user functionality not implemented yet.");
                                out.println();
                                break;
                            case 5:
                                controller.listALl(in,out);
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


}
