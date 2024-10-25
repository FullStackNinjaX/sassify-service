package com.globalsass.controller;

import com.globalsass.model.StyleEntity;
import com.globalsass.service.StyleService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseCookie;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/cookies")
public class CookiesController {

    @Autowired
    private StyleService styleService;

    @PostMapping("/create")
    public String createCookie(@RequestParam("styleId") Long styleId, HttpServletResponse response) {
        StyleEntity styleEntity = styleService.getStyleById(styleId);
        if (styleEntity != null) {
            ResponseCookie cookie = ResponseCookie.from("THEME_ID", styleEntity.getId().toString())
                    .path("/")
                    .maxAge(4800)
                    .sameSite("None")
                    .secure(true)
                    .build();
            response.addHeader("Set-Cookie", cookie.toString());
            return "Cookie created successfully!";
        } else {
            return "StyleEntity not found!";
        }
    }


    /*@PostMapping("/")
    public String createCookie(@RequestParam("styleId") Long styleId, HttpServletResponse response) {
        StyleEntity styleEntity = styleService.getStyleById(styleId);
        if (styleEntity != null) {
            Cookie cookie = new Cookie("THEME_ID", styleEntity.getId().toString());
            cookie.setPath("/");
            cookie.setMaxAge(4800);
            response.addCookie(cookie);
            return "Cookie created successfully!";
        } else {
            return "StyleEntity not found!";
        }

    }*/

    @GetMapping("/fetch-cookies")
    public Map<String, String> fetchVariables(@CookieValue("THEME_ID") Long styleId) {
        StyleEntity styleEntity = styleService.getStyleById(styleId);
        return styleEntity != null ? styleService.getSassVariables(styleId) : null;
    }
}