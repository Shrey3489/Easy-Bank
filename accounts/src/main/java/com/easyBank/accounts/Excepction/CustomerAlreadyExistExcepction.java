package com.easyBank.accounts.Excepction;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class CustomerAlreadyExistExcepction extends RuntimeException{

    public CustomerAlreadyExistExcepction(String message){
        super(message);
    }
}
