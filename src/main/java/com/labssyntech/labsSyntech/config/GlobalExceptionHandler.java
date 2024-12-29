package com.labssyntech.labsSyntech.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.labssyntech.labsSyntech.exception.ResourceAlredyExistsException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(ResourceAlredyExistsException.class)
    public ResponseEntity<String> handleResourceAlreadyExistsException(ResourceAlredyExistsException ex) {
        return ResponseEntity.status(409).body(ex.getMessage());
    }

    public ResponseEntity<String> handleGeneralException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno no servidor ;(");
    }
}
