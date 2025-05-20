package Grid;

import java.io.*;
import java.net.*;
import java.util.*;

public class IntegralServer {
    private static final int PORT = 12345;
    private static final int NUM_CLIENTS = 4;

    public static void main(String[] args) throws IOException {
        double a = 0;
        double b = Math.PI;
        int n = 100000; // Total de pasos
        int stepsPerClient = n / NUM_CLIENTS;
        double intervalSize = (b - a) / NUM_CLIENTS;

        ServerSocket serverSocket = new ServerSocket(PORT);
        List<Socket> clients = new ArrayList<>();

        System.out.println("Esperando clientes...");
        for (int i = 0; i < NUM_CLIENTS; i++) {
            Socket client = serverSocket.accept();
            clients.add(client);
            System.out.println("Cliente " + (i + 1) + " conectado");
        }

        // Enviar subintervalos
        for (int i = 0; i < NUM_CLIENTS; i++) {
            double subA = a + i * intervalSize;
            double subB = subA + intervalSize;

            DataOutputStream out = new DataOutputStream(clients.get(i).getOutputStream());
            out.writeDouble(subA);
            out.writeDouble(subB);
            out.writeInt(stepsPerClient);
        }

        // Recibir resultados
        double total = 0;
        for (Socket client : clients) {
            DataInputStream in = new DataInputStream(client.getInputStream());
            double partial = in.readDouble();
            total += partial;
            client.close();
        }

        serverSocket.close();
        System.out.println("Resultado total de la integral: " + total);
    }
}
