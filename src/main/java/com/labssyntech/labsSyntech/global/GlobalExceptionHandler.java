package com.labssyntech.labsSyntech.global;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.labssyntech.labsSyntech.exception.InternalErrorException;
import com.labssyntech.labsSyntech.exception.NotFoundException;
import com.labssyntech.labsSyntech.exception.ResourceAlredyExistsException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(ResourceAlredyExistsException.class)
    public ResponseEntity<String> handleResourceAlreadyExistsException(ResourceAlredyExistsException ex) {
        return ResponseEntity.status(409).body(ex.getMessage());
    }

    @ExceptionHandler(InternalErrorException.class)
    public ResponseEntity<String> handleGeneralException(Exception ex) {
        return ResponseEntity.status(500).body(ex.getMessage());
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> handleNotFoundException(NotFoundException ex) {
        return ResponseEntity.status(404).body(ex.getMessage());
    }
}
