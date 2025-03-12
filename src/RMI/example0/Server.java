package RMI.example0;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Server {
    public static void main(String[] args) {
        try {
            // Crear registro RMI en el puerto 1099
            Registry registry = LocateRegistry.createRegistry(1099);

            // Instanciar e registrar el objeto remoto
            Calculator calc = new CalculatorImpl();
            registry.rebind("CalculadoraServicio", calc);

            System.out.println("✅ Servidor RMI listo...");
        } catch (Exception e) {
            System.err.println("Error en servidor: " + e);
        }
    }
}