package RMI.example0;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Client {
    public static void main(String[] args) {
        try {
            // Conectar al RMI Registry del servidor (IP:puerto)
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);

            // Buscar el objeto remoto por nombre
            Calculator calc = (Calculator) registry.lookup("CalculadoraServicio");

            // Invocar método remoto
            int resultado = calc.sumar(5, 3);
            System.out.println("Resultado: " + resultado); // 8

        } catch (Exception e) {
            System.err.println("Error en cliente: " + e);
        }
    }
}