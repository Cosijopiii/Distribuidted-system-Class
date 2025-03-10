package Sockets.TCP.example2;

import java.io.*;
import java.net.*;

public class TCPServer {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(8080)) {
            System.out.println("Server is waiting for a connection...");

            try (Socket socket = serverSocket.accept();
                 ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream())) {

                System.out.println("Client connected!");

                // Receive and deserialize the object
                Student student = (Student) objectInputStream.readObject();
                System.out.println("Received Student Object: " + student);
            }

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
