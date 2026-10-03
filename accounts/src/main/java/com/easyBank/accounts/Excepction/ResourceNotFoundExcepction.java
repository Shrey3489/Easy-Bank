package com.easyBank.accounts.Excepction;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundExcepction extends RuntimeException{

    public ResourceNotFoundExcepction(String resourceName, String fieldNAME, String filedValue)
    {
        super(String.format("%s not found with the given input data %s: '%s'",resourceName,fieldNAME,filedValue));
    }
}
