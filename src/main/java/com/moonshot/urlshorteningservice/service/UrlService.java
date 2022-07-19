package com.moonshot.urlshorteningservice.service;

import com.moonshot.urlshorteningservice.datatransfer.UrlDto;
import com.moonshot.urlshorteningservice.model.Url;

import java.util.List;

public interface UrlService {

    Url createShortLink(UrlDto urlDto);
    Url persistShortLink(Url url);
    Url getHashedUrl(String url);
    List<Url> getAllUrls();
    void deleteShortLink(Url url);
}
