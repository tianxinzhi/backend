package com.pccw.backend.exception;

/**
 * SMPException
 */
public class SMPException extends RuntimeException {



    private static final long serialVersionUID = 4564124491192825748L;

    private int code;

    public SMPException() {
        super();
    }

    public SMPException(int code, String message) {
        super(message);
        this.setCode(code);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    
}



