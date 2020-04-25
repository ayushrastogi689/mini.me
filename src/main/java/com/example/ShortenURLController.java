package com.example;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller public class ShortenURLController {

  @GetMapping("/") public String loadIndex() {
    return "index";
  }

}
