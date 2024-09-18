import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientSide {
    public static void main(String[] args) {
        int port = 1234;
        String ip = "localhost";
        Scanner scanner = new Scanner(System.in);

        try (Socket socket = new Socket(ip, port)) {
            System.out.println("Connected to server on port " + port);
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.isEmpty()) {
                    System.out.print("Enter your choice: ");
                    int choice = scanner.nextInt();
                    writer.println(choice);

                    if (choice == 0) {
                        System.out.println("Exiting...");
                        break;
                    }

                    String response = reader.readLine();
                    if (response == null) {
                        System.out.println("Server closed the connection.");
                        break;
                    }
                    System.out.println("Server response: " + response);

                    if (choice == 1) {
                        System.out.print("Enter ID: ");
                        int id = scanner.nextInt();
                        writer.println(id);
                        response = reader.readLine();
                        if (response == null) {
                            System.out.println("Server closed the connection.");
                            break;
                        }
                        System.out.println("Server response: " + response);
                    }
                    if(choice == 2){
                        Long id = scanner.nextLong();
                        writer.println(id);
                        response = reader.readLine();
                        if (response == null) {
                            System.out.println("Server closed the connection.");
                            break;
                        }
                        System.out.println("Server response: " + response);
                    }
                } else {
                    System.out.println(line);
                }
            }
        } catch (IOException e) {
            System.out.println("An error occurred: " + e.getMessage());
            e.printStackTrace();
        }
        scanner.close();
    }
}
