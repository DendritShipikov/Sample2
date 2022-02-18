package com.toto.sample2.exceptions;

public class UserAlreadyExistsException extends Exception {

    public UserAlreadyExistsException() { super(); }

    public UserAlreadyExistsException(String message) { super(message); }

    public UserAlreadyExistsException(Throwable cause) { super(cause); }
    
}