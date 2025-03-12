package Sockets.TCP.example2;

import java.io.*;
import java.net.*;
import java.util.Arrays;

public class TCPClient {
    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 8080);
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream())) {

            // Create Address object
            Address address = new Address("123 Main St", "New York", "USA");

            // Create Course objects
            Course course1 = new Course("Mathematics", 4);
            Course course2 = new Course("Computer Science", 5);

            // Create Student object
            Student student = new Student("Alice Johnson", 22, Arrays.asList(course1, course2), address);

            // Send Student object to the server
            objectOutputStream.writeObject(student);
            System.out.println("Student object sent!");
        } catch (IOException e) {
            e.printStackTrace();


        }
    }
}
