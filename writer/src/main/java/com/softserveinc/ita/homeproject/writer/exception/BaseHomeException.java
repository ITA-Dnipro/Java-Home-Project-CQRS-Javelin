package com.softserveinc.ita.homeproject.writer.exception;

public abstract class BaseHomeException extends RuntimeException {
    protected BaseHomeException(String message) {
        super(message);
    }
}
