package com.moonshot.urlShorteningService.datatransfer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UrlResponseDto {
    private String userProvidedUrl;
    private String shortUrlLink;
    private LocalDateTime expiryDate;
}
