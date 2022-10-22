package com.fse.exception;

public class CompanyException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public CompanyException(String message) {
        super(message);
    }
}

