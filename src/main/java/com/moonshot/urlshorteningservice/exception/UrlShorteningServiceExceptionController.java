package com.moonshot.urlshorteningservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class UrlShorteningServiceExceptionController {

    @ExceptionHandler(value = UrlNotProvidedException.class)
    public ResponseEntity<Object> exception(UrlNotProvidedException exception) {
        return new ResponseEntity<>(exception.getUserMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = ConnectionToDatabaseException.class)
    public ResponseEntity<Object> exception(ConnectionToDatabaseException exception) {
        return new ResponseEntity<>(exception.getUserMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = UrlDoesNotExistException.class)
    public ResponseEntity<Object> exception(UrlDoesNotExistException exception) {
        return new ResponseEntity<>(exception.getUserMessage(), HttpStatus.NOT_FOUND);
    }


}
