package RMI.example1;

import java.rmi.*;

public interface BankService extends Remote {
    Account createAccount(String name, double initialBalance) throws RemoteException;
    Account getAccount(int accountNumber) throws RemoteException, InvalidAccountException;
}