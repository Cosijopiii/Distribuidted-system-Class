package RMI.example0;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class CalculatorImpl extends UnicastRemoteObject implements Calculator {
    public CalculatorImpl() throws RemoteException {
        super(); // Puerto aleatorio si no se especifica
    }

    @Override
    public int sumar(int a, int b) {
        return a + b;
    }
}