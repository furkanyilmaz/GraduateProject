package com.furkanyilmaz.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalHandlingException {

    @ExceptionHandler({FurkanYilmazException.class})
    public String handlingNotFoundException(){
        return "böyle bir daha yok.";

    }
   //@ExceptionHandler({NullPointerException.class})
    //null değer olduğunda yakalar.
}
