package Sockets.TCP.example1;

import java.io.*;
import java.net.*;

public class Server {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(8080)) {
            System.out.println("Sockets.TCP.example1.Server started. Waiting for client...");

            // Wait for client connection
            try (Socket clientSocket = serverSocket.accept();
                 BufferedReader in = new BufferedReader
                         (new InputStreamReader(clientSocket.getInputStream()));
                 PrintWriter out = new PrintWriter
                         (clientSocket.getOutputStream(), true)) {

                System.out.println("Sockets.TCP.example1.Client connected: "
                        + clientSocket.getInetAddress());

                // Read message from client
                String received = in.readLine();
                System.out.println("Received from client: " + received);

                // Send response to client
                out.println("Hello from server");
                System.out.println("Response sent to client");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}