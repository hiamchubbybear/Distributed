
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class DependencyInjection {
    public java.net.ServerSocket serverSocket(int port) throws IOException {
        java.net.ServerSocket serverSocket = new java.net.ServerSocket(port);
        return serverSocket;
    }

    public Socket socket(int port, String ip) throws IOException {
        Socket socket = new Socket(ip, port);
        return socket;
    }

    public PrintWriter writer(Socket socket) throws IOException {
        PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
        return writer;
    }

    public BufferedReader reader(Socket socket) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        return reader;
    }
}
