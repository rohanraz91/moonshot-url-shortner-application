package com.moonshot.urlShorteningService.service;

import com.moonshot.urlShorteningService.exception.UrlNotProvidedException;
import com.moonshot.urlShorteningService.repository.UrlRepository;
import org.apache.commons.lang3.StringUtils;
import com.google.common.hash.Hashing;
import com.moonshot.urlShorteningService.datatransfer.UrlDto;
import com.moonshot.urlShorteningService.model.Url;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class UrlServiceImpl implements UrlService {

    @Autowired
    private UrlRepository urlRepo;

    @Override
    public Url createShortLink(UrlDto urlDto) {
        if(StringUtils.isNotEmpty(urlDto.getUrl())){
            String hashedUrl = hashUrl(urlDto.getUrl());
            final Url persistUrl = new Url();
            persistUrl.setUserProvidedUrl(urlDto.getUrl());
            persistUrl.setCreationDate(LocalDateTime.now());
            persistUrl.setShortUrlLink(hashedUrl);
            persistUrl.setExpiryDate(getExpiryDate(urlDto.getExpiryDate(), persistUrl.getCreationDate()));
            Url returnSavedUrl = persistShortLink(persistUrl);

            if( null != returnSavedUrl){
                return returnSavedUrl;
            }else
                return null;
        }
        throw new UrlNotProvidedException("Url not provided by user");
    }

    private String hashUrl(String url) {
        String hashedUrl = "";
        //need to tackle same url request from user to append time
        LocalDateTime currentTime = LocalDateTime.now();
        hashedUrl = Hashing.murmur3_32()
                      .hashString(url.concat(currentTime.toString()), StandardCharsets.UTF_8)
                      .toString(); // creates a short url
        return hashedUrl;
    }

    @Override
    public Url persistShortLink(Url url) {
        return urlRepo.save(url);
    }

    @Override
    public Url getHashedUrl(String url) {
        if(StringUtils.isNotEmpty(url)){
            return urlRepo.findByShortUrlLink(url).get();
        }else{
            throw new UrlNotProvidedException("Short link not provided by user.");
        }
    }

    @Override
    public List<Url> getAllUrls() {
        return urlRepo.findAll();
    }

    //Below are optional methods (Future?)
    @Override
    public void deleteShortLink(Url url) {
        urlRepo.delete(url);
    }

    private LocalDateTime getExpiryDate(String expiryDate, LocalDateTime creationDate) {
        if(StringUtils.isBlank(expiryDate)){
            return creationDate.plusMinutes(2);
        }else {
            LocalDateTime expiryDateByUser = LocalDateTime.parse(expiryDate);
            return expiryDateByUser;
        }
    }
}
