package com.moonshot.urlshorteningservice.utils;

import com.moonshot.urlshorteningservice.model.Url;

import java.time.LocalDateTime;

public class UrlObjectUtils {

    private static final String URL = "https://docs.google.com/document/d/1F94rh4HATfua566ZngAOv6q-MbnO0mGHTA2XHIhlsiM/edit";

    public static Url createUrlObject(){
        Url urlObj = new Url();
        urlObj.setShortUrlLink("1234#");
        urlObj.setCreationDate(LocalDateTime.now());
        urlObj.setUserProvidedUrl(URL);
        urlObj.setExpiryDate(LocalDateTime.now());

        return urlObj;
    }
}
