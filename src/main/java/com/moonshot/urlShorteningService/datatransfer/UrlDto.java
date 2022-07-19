package com.moonshot.urlShorteningService.datatransfer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UrlDto {

    private String url;
    //Optional
    private String expiryDate;
}
