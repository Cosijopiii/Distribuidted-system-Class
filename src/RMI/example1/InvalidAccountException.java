package RMI.example1;

import java.io.Serializable;

public class InvalidAccountException extends Exception implements Serializable {
    public InvalidAccountException(String message) { super(message); }
}