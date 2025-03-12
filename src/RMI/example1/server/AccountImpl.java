package RMI.example1.server;

import RMI.example1.*;

import java.rmi.*;
import java.rmi.server.*;

public class AccountImpl extends UnicastRemoteObject implements Account {
    private final int accountNumber;
    private final String accountHolder;
    private double balance;

    public AccountImpl(int number, String holder, double initialBalance) throws RemoteException {
        super();
        this.accountNumber = number;
        this.accountHolder = holder;
        this.balance = initialBalance;
    }

    @Override public synchronized void deposit(double amount) throws RemoteException {
        balance += amount;
    }

    @Override public synchronized void withdraw(double amount) throws RemoteException, InsufficientFundsException {
        if (balance < amount) throw new InsufficientFundsException("Fondos insuficientes");
        balance -= amount;
    }

    // Getters
    @Override public int getAccountNumber() { return accountNumber; }
    @Override public String getAccountHolder() { return accountHolder; }
    @Override public double getBalance() { return balance; }
}