package com.example.demomdclogging.exception;

public class TransferException extends Exception {
    public TransferException(Throwable reason) {
        super("Error in Transfer. Message: " + reason.getMessage(), reason);
    }
}
