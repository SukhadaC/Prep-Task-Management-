package com.preparation.prep.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;


public class ResourceNotFoundException extends RuntimeException{
    public ResourceNotFoundException(String mssg)
    {
        super(mssg);
    }

}
