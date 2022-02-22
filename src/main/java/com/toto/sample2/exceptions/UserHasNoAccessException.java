package com.toto.sample2.exceptions;

public class UserHasNoAccessException extends Exception {

    public UserHasNoAccessException() { super(); }

    public UserHasNoAccessException(String message) { super(message); }

    public UserHasNoAccessException(Throwable cause) { super(cause); }
    
}