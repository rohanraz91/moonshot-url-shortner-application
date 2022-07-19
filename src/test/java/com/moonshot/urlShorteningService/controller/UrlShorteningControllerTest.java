

package com.moonshot.urlShorteningService.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.moonshot.urlShorteningService.exception.ConnectionToDatabaseException;
import com.moonshot.urlShorteningService.exception.UrlDoesNotExistException;
import com.moonshot.urlShorteningService.model.Url;
import com.moonshot.urlShorteningService.service.UrlService;
import com.moonshot.urlShorteningService.utils.UrlObjectUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(UrlShorteningController.class)
public class UrlShorteningControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;
    @MockBean
    private UrlService urlService;

    @Test
    void verifyGivenPostRequestOnUrlShorteningThenExpectedStatusCodeIsReturned() throws Exception {

        Url urlObj = UrlObjectUtils.createUrlObject();
        when(urlService.createShortLink(any())).thenReturn(urlObj);
        this.mockMvc
                .perform(MockMvcRequestBuilders.post("/url-link"))
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(view().name("redirect:/url-link"));
    }

    @Test
    void verifyGivenGetRequestOnUrlShorteningThenExpectedStatusCodeIsReturned() throws Exception {

        Url urlObj = UrlObjectUtils.createUrlObject();
        List<Url> urlList = Collections.singletonList(urlObj);
        when(urlService.getAllUrls()).thenReturn(urlList);
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/url-link"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(view().name("urls"));
    }

    @Test
    void verifyGivenGetRequestOnShortenedLinkThenExpectedStatusCodeIsReturned() throws Exception {

        Url urlObj = UrlObjectUtils.createUrlObject();
        when(urlService.getHashedUrl(any())).thenReturn(urlObj);
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/{shortenedLink}","1234H6"))
                .andExpect(MockMvcResultMatchers.redirectedUrl(urlObj.getUserProvidedUrl()))
                .andExpect(MockMvcResultMatchers.status().isFound());
    }

    @Test
    void verifyGivenPostRequestOnUrlShorteningThenExpectedExceptionIsReturned() throws Exception {

        when(urlService.createShortLink(any())).thenReturn(any());
        this.mockMvc
                .perform(MockMvcRequestBuilders.post("/url-link"))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ConnectionToDatabaseException));
    }

    @Test
    void verifyGivenGetRequestOnShortenedLinkThenExpectedExceptionIsReturned() throws Exception {

        when(urlService.getHashedUrl(any())).thenReturn(any());
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/{shortenedLink}","1234H6"))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof UrlDoesNotExistException));
    }
}
