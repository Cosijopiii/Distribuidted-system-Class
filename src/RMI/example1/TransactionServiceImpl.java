package RMI.example1;

import java.rmi.*;
import java.rmi.server.*;

public class TransactionServiceImpl extends UnicastRemoteObject implements TransactionService {

    public TransactionServiceImpl() throws RemoteException {
        super(); // Llama al constructor de UnicastRemoteObject
    }

    @Override
    public void transfer(Account from, Account to, double amount)
            throws RemoteException, InsufficientFundsException, InvalidAccountException {

        try {
            // Retirar de la cuenta origen
            from.withdraw(amount);

            // Depositar en la cuenta destino
            to.deposit(amount);

        } catch (RemoteException e) {
            // Rollback en caso de error
            try {
                from.deposit(amount); // Revertir el retiro
            } catch (RemoteException ex) {
                throw new RemoteException("Error en rollback: " + ex.getMessage());
            }
            throw e;
        }
    }
}