package com.furkanyilmaz.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalHandlingException {

    //Hiç değer olduğunda spring yakalar.
    @ExceptionHandler({FurkanYilmazException.class})
    public String handlingNotFoundException(){
        return "There is no data";
    }

    //null olduğumda spring yakalar.
    @ExceptionHandler({NullPointerException.class})
    public String handlingNullPointerException(){
        return "null value entered.";
    }


}
