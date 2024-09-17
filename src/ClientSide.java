
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

            System.out.println("Port to be used");
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            while (true) {

                System.out.println("Please input a number ");
                Long s = scanner.nextLong();
                if (s == 0)
                    break;
                writer.println(s);
                String s1 = reader.readLine();
                System.out.println(s1);

            }
            writer.print(scanner.nextLong());
            scanner.close();
            socket.close();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
