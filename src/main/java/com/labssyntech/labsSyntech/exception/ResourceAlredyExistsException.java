package com.labssyntech.labsSyntech.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ResourceAlredyExistsException extends RuntimeException {
    public ResourceAlredyExistsException(String message) {
        super(message);
    }
}
