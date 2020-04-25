package com.example;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController public class URLShortenRestController {

  private Map<String, ShortenURL> shortenUrlMap = new HashMap<>();

  @PostMapping("/shortenurl") public ResponseEntity<Object> getShortenURL(
      @RequestBody ShortenURL shortenURL) throws MalformedURLException {
    String randomChar = getRandomChars();
    setShortUrl(randomChar, shortenURL);
    return new ResponseEntity<Object>(shortenURL, HttpStatus.OK);

  }

  private void setShortUrl(String randomChar, ShortenURL shortenURL) throws MalformedURLException {
    shortenURL.setShortURL("http://localhost:8080/s/" + randomChar);
    shortenUrlMap.put(randomChar, shortenURL);
  }

  @GetMapping("/s/{randomstring}") public void getFullUrl(
      HttpServletResponse response,
      @PathVariable("randomstring") String randomString) throws IOException {
    String fullUrl = shortenUrlMap.get(randomString).getFullURL();
    response.sendRedirect(sanitize(fullUrl));
  }

  private String sanitize(String fullUrl) {
    if (fullUrl.contains("http://") || fullUrl.contains("https://")) {
      return fullUrl;
    } else {
      return "https://" + fullUrl;
    }
  }

  private String getRandomChars() {
    String randomStr = "";
    String possibleStr = "ASDFGHJKLQWERTYUIOPZXCVBNMasdfghjklqwertyuiopzxcvbnm0123456789";

    for (int i = 0; i < 5; i++)
      randomStr += possibleStr.charAt((int) Math.floor(Math.random() * possibleStr.length()));
    return randomStr;

  }

}
