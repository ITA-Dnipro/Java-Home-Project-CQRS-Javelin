package com.softserveinc.ita.homeproject.reader.service.exception;


public abstract class BaseHomeException extends RuntimeException {

    protected BaseHomeException(String message) {
        super(message);
    }
}
