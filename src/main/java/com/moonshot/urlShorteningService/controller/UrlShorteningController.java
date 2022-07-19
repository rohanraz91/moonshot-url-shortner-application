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

    @Autowired
    private UrlService urlService;

    @GetMapping("/urlLink")
    public String getShortUrl(Model model) {
        List<Url> urls = urlService.getAllUrls();
        model.addAttribute("urls", urls);
        model.addAttribute("urlDto", new UrlDto());
        return "urls";
    }
    @PostMapping("/urlLink")
    public String postShortLink(/*Model model,*/ @ModelAttribute UrlDto urlDto){
        Url urlReturned = urlService.createShortLink(urlDto);

        if(null != urlReturned){
            UrlResponseDto urlResponseDto = new UrlResponseDto();
            urlResponseDto.setUserProvidedUrl(urlReturned.getUserProvidedUrl());
            urlResponseDto.setShortUrlLink(urlReturned.getShortUrlLink());
            urlResponseDto.setExpiryDate(urlReturned.getExpiryDate());
            return "redirect:/urlLink";
        }
        throw new ConnectionToDatabaseException("Error Processing Request, try after sometime");
    }

    /*@PostMapping("/urlLink")
    public ResponseEntity<?> postShortLink(@RequestBody UrlDto urlDto){
        Url urlReturned = urlService.createShortLink(urlDto);

        if(null != urlReturned){
            UrlResponseDto urlResponseDto = new UrlResponseDto();
            urlResponseDto.setUserProvidedUrl(urlReturned.getUserProvidedUrl());
            urlResponseDto.setShortUrlLink(urlReturned.getShortUrlLink());
            urlResponseDto.setExpiryDate(urlReturned.getExpiryDate());
            return new ResponseEntity<UrlResponseDto>(urlResponseDto, HttpStatus.OK);
        }
        throw new ConnectionToDatabaseException("Error Processing Request, try after sometime");
    }*/

    @GetMapping("/{shortenedLink}")
    public ResponseEntity<?> getOriginalUrl(@PathVariable String shortenedLink, HttpServletResponse response) throws IOException {

        Url originalUrl = urlService.getHashedUrl(shortenedLink);
        if(null == originalUrl){
            throw new UrlDoesNotExistException("Short Url does not exists");
        }else{
            response.sendRedirect(originalUrl.getUserProvidedUrl());
            return null;
        }

    }
}
