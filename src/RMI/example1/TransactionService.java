package RMI.example1;

import java.rmi.*;

public interface TransactionService extends Remote {
    void transfer(Account from, Account to, double amount)
            throws RemoteException, InsufficientFundsException, InvalidAccountException;
}