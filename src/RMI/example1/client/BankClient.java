package RMI.example1.client;

import java.rmi.*;

import RMI.example1.*;

public class BankClient {
    public static void main(String[] args) {
        try {

            BankService bank = (BankService) Naming.lookup("rmi://localhost/BankService");
            TransactionService transactions = (TransactionService) Naming.lookup("rmi://localhost/TransactionService");

            // Crear cuentas
            Account alice = bank.createAccount("Alice", 5000);
            Account bob = bank.createAccount("Bob", 3000);

            // Mostrar saldos iniciales
            System.out.println("💰 Saldo inicial Alice: " + alice.getBalance());
            System.out.println("💰 Saldo inicial Bob: " + bob.getBalance());

            // Transferir 1500 de Alice a Bob
            transactions.transfer(alice, bob, 1500);
            System.out.println("\n✅ Transferencia exitosa!");

            // Mostrar saldos finales
            System.out.println("\n💳 Saldo final Alice: " + alice.getBalance());
            System.out.println("💳 Saldo final Bob: " + bob.getBalance());

        } catch (InsufficientFundsException e) {
            System.err.println("❌ Error: " + e.getMessage());
        } catch (InvalidAccountException e) {
            System.err.println("❌ Cuenta inválida: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("❌ Error general: " + e);
        }
    }
}