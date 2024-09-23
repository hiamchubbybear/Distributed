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
            StudentService service = new StudentService();
            StudentController controller = new StudentController();
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
                                controller.getById(in, out);
                                break;
                            case 3:
                                controller.deleteById(in, out);
                                break;
                            case 2:
                                controller.addUser(in, out);
                                break;
                            case 4:
                                controller.updateStudent(out, in);
                                break;
                            case 5:
                                controller.listALl(in, out);
                                break;
                            case 6:
                                controller.findByName(out, in);
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
