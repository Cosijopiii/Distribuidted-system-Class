package RMI.example1.server;

import RMI.example1.*;

import java.rmi.*;
import java.rmi.server.*;
import java.util.concurrent.ConcurrentHashMap;

public class BankServiceImpl extends UnicastRemoteObject implements BankService {
    private final ConcurrentHashMap<Integer, Account> accounts = new ConcurrentHashMap<>();
    private int accountCounter = 1000;

    public BankServiceImpl() throws RemoteException {}

    @Override
    public Account createAccount(String name, double initialBalance) throws RemoteException {
        int accountNumber = ++accountCounter;
        Account account = new AccountImpl(accountNumber, name, initialBalance);
        accounts.put(accountNumber, account);
        return account;
    }

    @Override
    public Account getAccount(int accountNumber) throws RemoteException, InvalidAccountException {
        Account account = accounts.get(accountNumber);
        if (account == null) throw new InvalidAccountException("Cuenta no existe: " + accountNumber);
        return account;
    }
}