package com.moonshot.urlShorteningService.exception;

import com.moonshot.urlShorteningService.datatransfer.UrlDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

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
