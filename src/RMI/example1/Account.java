package RMI.example1;

import java.rmi.*;

public interface Account extends Remote {
    int getAccountNumber() throws RemoteException;
    String getAccountHolder() throws RemoteException;
    double getBalance() throws RemoteException;
    void deposit(double amount) throws RemoteException;
    void withdraw(double amount) throws RemoteException, InsufficientFundsException;
}