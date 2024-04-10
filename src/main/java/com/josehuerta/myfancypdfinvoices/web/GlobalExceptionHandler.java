package com.josehuerta.myfancypdfinvoices.web;


import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public String handlemethodArgumentNotValid(MethodArgumentNotValidException exception) {
        // TODO custom object here
        return "Sorry, that wasn't quite right: " + exception.getMessage();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public String handleConstraintArgumentNotValid(ConstraintViolationException exception) {
        // TODO custom object here
        return "Sorry, that wasn't quite right: " + exception.getMessage();
    }
}
