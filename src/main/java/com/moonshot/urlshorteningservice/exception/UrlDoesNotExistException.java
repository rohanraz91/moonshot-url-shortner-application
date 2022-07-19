package com.moonshot.urlshorteningservice.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UrlDoesNotExistException extends RuntimeException{
    private static final long serialVersionUID = 1L;
    private final String userMessage;
}
