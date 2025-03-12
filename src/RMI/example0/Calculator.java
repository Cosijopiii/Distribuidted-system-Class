package RMI.example0;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Calculator extends Remote {
    int sumar(int a, int b) throws RemoteException;
}