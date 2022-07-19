package com.moonshot.urlshorteningservice.service;

import com.moonshot.urlshorteningservice.datatransfer.UrlDto;
import com.moonshot.urlshorteningservice.model.Url;
import com.moonshot.urlshorteningservice.repository.UrlRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;


import java.time.LocalDateTime;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UrlServiceImplTest {
    private static final String URL = "https://docs.google.com/document/d/1F94rh4HATfua566ZngAOv6q-MbnO0mGHTA2XHIhlsiM/edit";

    @Mock
    UrlRepository urlRepository;
    @InjectMocks
    UrlServiceImpl urlService;

    @Test
    public void when_createShortLink_then_return_urlObj(){
        UrlDto urlDto = new UrlDto();
        urlDto.setUrl(URL);
        Url urlObj = new Url();
        urlObj.setShortUrlLink("1234#");
        urlObj.setCreationDate(LocalDateTime.now());
        urlObj.setUserProvidedUrl(URL);
        urlObj.setExpiryDate(LocalDateTime.now());


        when(urlRepository.save(any(Url.class))).thenReturn(urlObj);
        Url shortUrl =  urlService.createShortLink(urlDto);
        assertThat(shortUrl.getUserProvidedUrl(), equalTo(URL));
        assertThat(shortUrl.getShortUrlLink(),notNullValue());
    }
}
