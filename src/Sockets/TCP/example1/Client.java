package Sockets.TCP.example1;

import java.io.*;
import java.net.*;

public class Client {
    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 8080);
             PrintWriter out = new PrintWriter(socket.getOutputStream(),
                     true);
             BufferedReader in = new BufferedReader(new
                     InputStreamReader(socket.getInputStream()))) {

            System.out.println("Connected to server");

            // Send message to server
            out.println("Hello from client");
            System.out.println("Message sent to server");

            // Receive response from server
            String response = in.readLine();
            System.out.println("Received from server: " + response);


       switch (response) {
            case "Hello from client" -> out.println("Hello from client");
            case "Message sent to server" -> out.println("Message sent to server");
}
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}