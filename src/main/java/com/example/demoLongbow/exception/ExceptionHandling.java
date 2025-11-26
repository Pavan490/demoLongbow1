package com.example.demoLongbow.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;
@RestControllerAdvice
public class ExceptionHandling {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String,String>> exceptionHandling(Exception ex){
        Map<String,String>error=new HashMap<>();
        error.put("result","failed");
        error.put("message",ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,Object>> methodHandlingErrors(MethodArgumentNotValidException exception){
        Map<String,String>errors=new HashMap<>();
        exception.getBindingResult().getFieldErrors().forEach(err->{
            errors.put(err.getField(),err.getDefaultMessage());});
        Map<String,Object> response=new HashMap<>();
        response.put("result","failed");
        response.put("errors",errors);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}
