package Sockets.UDP.example1;

import java.io.IOException;
import java.net.*;

public class UDPServer {
    public static void main(String[] args) {
        try (DatagramSocket socket = new DatagramSocket(8080)) {
            byte[] buffer = new byte[1024];
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

            System.out.println("Sockets.TCP.example1.Server started. Waiting for client...");

            while (true) {
                // Receive packet from client
                socket.receive(packet);
                String received = new String(packet.getData(), 0, packet.getLength());
                System.out.println("Received from client: " + received);

                // Send response to client
                String response = "Hello from server";
                byte[] responseData = response.getBytes();
                DatagramPacket responsePacket = new DatagramPacket(responseData,
                        responseData.length, packet.getAddress(), packet.getPort());
                socket.send(responsePacket);
                System.out.println("Response sent to client");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}