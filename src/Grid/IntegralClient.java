package Grid;

import java.io.*;
import java.net.*;

public class IntegralClient {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 12345);
        DataInputStream in = new DataInputStream(socket.getInputStream());
        DataOutputStream out = new DataOutputStream(socket.getOutputStream());

        // Recibir datos
        double a = in.readDouble();
        double b = in.readDouble();
        int n = in.readInt();

        // Calcular integral parcial
        double h = (b - a) / n;
        double sum = 0.5 * (f(a) + f(b));
        for (int i = 1; i < n; i++) {
            sum += f(a + i * h);
        }
        double result = sum * h;

        // Enviar resultado
        out.writeDouble(result);

        // Cerrar
        socket.close();
    }

    private static double f(double x) {
        return Math.sin(x); // Cambia por la función que quieras
    }
}
