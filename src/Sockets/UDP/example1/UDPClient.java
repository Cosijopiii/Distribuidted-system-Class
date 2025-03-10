package Sockets.UDP.example1;

import java.io.IOException;
import java.net.*;

public class UDPClient {
    public static void main(String[] args) {
        try (DatagramSocket socket = new DatagramSocket()) {
            InetAddress address = InetAddress.getByName("localhost");
            byte[] buffer = "Hello from client".getBytes();

            // Send packet to server
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, 8080);
            socket.send(packet);
            System.out.println("Message sent to server");
            //add a random comm
            // Receive response from server
            byte[] responseBuffer = new byte[1024];
            DatagramPacket responsePacket = new DatagramPacket(responseBuffer, responseBuffer.length);
            socket.receive(responsePacket);
            String response = new String(responsePacket.getData(), 0, responsePacket.getLength());
            System.out.println("Received from server: " + response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}