

package com.moonshot.urlShorteningService.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.moonshot.urlShorteningService.model.Url;
import com.moonshot.urlShorteningService.service.UrlService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(UrlShorteningController.class)
public class UrlShorteningControllerTest {

    private static final String URL = "https://docs.google.com/document/d/1F94rh4HATfua566ZngAOv6q-MbnO0mGHTA2XHIhlsiM/edit";
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;
    @MockBean
    private UrlService urlService;

    @Test
    void verifyGivenPostRequestOnUrlShortnerThenExpectedStatusCodeIsReturned() throws Exception {
        Url urlObj = new Url();
        urlObj.setShortUrlLink("1234#");
        urlObj.setCreationDate(LocalDateTime.now());
        urlObj.setUserProvidedUrl(URL);
        urlObj.setExpiryDate(LocalDateTime.now());
        when(urlService.createShortLink(any())).thenReturn(urlObj);
        this.mockMvc
                .perform(MockMvcRequestBuilders.post("/urlLink"))
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(view().name("redirect:/urlLink"));
    }
    @Test
    void verifyGivenGetRequestOnUrlShortnerThenExpectedStatusCodeIsReturned() throws Exception {
        Url urlObj = new Url();
        urlObj.setShortUrlLink("1234#");
        urlObj.setCreationDate(LocalDateTime.now());
        urlObj.setUserProvidedUrl(URL);
        urlObj.setExpiryDate(LocalDateTime.now());
        List<Url> urlList = Collections.singletonList(urlObj);
        when(urlService.getAllUrls()).thenReturn(urlList);
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/urlLink"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(view().name("urls"));
    }
}
