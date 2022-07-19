package com.moonshot.urlShorteningService.controller;

import com.moonshot.urlShorteningService.datatransfer.UrlDto;
import com.moonshot.urlShorteningService.datatransfer.UrlResponseDto;
import com.moonshot.urlShorteningService.exception.ConnectionToDatabaseException;
import com.moonshot.urlShorteningService.exception.UrlDoesNotExistException;
import com.moonshot.urlShorteningService.model.Url;
import com.moonshot.urlShorteningService.service.UrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
public class UrlShorteningController {

    private static final String ERROR_PROCESSING = "Error Processing Request, try after sometime";
    private static final String URL_DOEST_NOT_EXIST = "Short Url does not exists";
    @Autowired
    private UrlService urlService;

    @GetMapping("/url-link")
    public String getShortUrl(Model model) {
        List<Url> urls = urlService.getAllUrls();
        model.addAttribute("urls", urls);
        model.addAttribute("urlDto", new UrlDto());
        return "urls";
    }

    @PostMapping("/url-link")
    public String postShortLink(/*Model model,*/ @ModelAttribute UrlDto urlDto){
        Url urlReturned = urlService.createShortLink(urlDto);

        if(null != urlReturned){
            //Below can be used when we want to return ResponseEntity
            UrlResponseDto urlResponseDto = new UrlResponseDto();
            urlResponseDto.setUserProvidedUrl(urlReturned.getUserProvidedUrl());
            urlResponseDto.setShortUrlLink(urlReturned.getShortUrlLink());
            urlResponseDto.setExpiryDate(urlReturned.getExpiryDate());
            return "redirect:/url-link";
            // return new ResponseEntity<UrlResponseDto>(urlResponseDto, HttpStatus.OK);
        }
        throw new ConnectionToDatabaseException(ERROR_PROCESSING);
    }

    @GetMapping("/{shortenedLink}")
    public ResponseEntity<?> getOriginalUrl(@PathVariable String shortenedLink, HttpServletResponse response) throws IOException {

        Url originalUrl = urlService.getHashedUrl(shortenedLink);
        if(null == originalUrl){
            throw new UrlDoesNotExistException(URL_DOEST_NOT_EXIST);
        }else{
            response.sendRedirect(originalUrl.getUserProvidedUrl());
            return null;
        }
    }
}
