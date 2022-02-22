package com.toto.sample2.exceptions;

public class WrongLoginException extends Exception {

    public WrongLoginException() { super(); }

    public WrongLoginException(String message) { super(message); }

    public WrongLoginException(Throwable cause) { super(cause); }

}