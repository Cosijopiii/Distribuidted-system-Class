package RMI.example1.server;
import RMI.example1.*;

import java.rmi.*;
import java.rmi.registry.LocateRegistry;

public class BankServer {
    public static void main(String[] args) {
        try {
            LocateRegistry.createRegistry(1099); // Crear registro RMI
            System.setProperty("java.rmi.server.hostname", "localhost"); // Usar IP real en red

            BankService bankService = new BankServiceImpl();
            TransactionService transactionService = new TransactionServiceImpl();

            Naming.rebind("BankService", bankService);
            Naming.rebind("TransactionService", transactionService);

            System.out.println("✅ Servidor RMI activo en puerto 1099");
        } catch (Exception e) {
            System.err.println("❌ Error en servidor: " + e);
            e.printStackTrace();
        }
    }
}