package com.preparation.prep.exception;

public class ExpiredTokenException extends RuntimeException{

    public  ExpiredTokenException(String mssg)
    {
        super(mssg);
    }

}
