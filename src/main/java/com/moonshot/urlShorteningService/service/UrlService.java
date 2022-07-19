package com.moonshot.urlShorteningService.service;

import com.moonshot.urlShorteningService.datatransfer.UrlDto;
import com.moonshot.urlShorteningService.model.Url;
import org.springframework.stereotype.Service;

import java.util.List;

public interface UrlService {

    Url createShortLink(UrlDto urlDto);
    Url persistShortLink(Url url);
    Url getHashedUrl(String url);
    List<Url> getAllUrls();
    void deleteShortLink(Url url);
}
