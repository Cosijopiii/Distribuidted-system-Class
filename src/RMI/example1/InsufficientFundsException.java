package RMI.example1;

import java.io.Serializable;

public class InsufficientFundsException extends Exception implements Serializable {
    public InsufficientFundsException(String message) { super(message); }
}