package com.moonshot.urlShorteningService.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UrlNotProvidedException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private final String userMessage;
}
