package com.globalsass.controller;

import com.globalsass.model.StyleEntity;
import com.globalsass.service.StyleService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
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
            // Create a cookie using ResponseCookie
            ResponseCookie cookie = ResponseCookie.from("THEME_ID", styleEntity.getId().toString())
                    .domain("localhost")
                    .path("/")
                    .maxAge(4800) // 4800 seconds, adjust as necessary
                    .sameSite("Lax") // Helps mitigate CSRF attacks
                    .httpOnly(false) // Prevents JavaScript access to the cookie
                    .secure(false) // Change to true if your app is using HTTPS
                    .build();

            // Add the Set-Cookie header
            response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

            return "Cookie created successfully!";
        } else {
            return "StyleEntity not found!";
        }
    }

    @GetMapping("/read-spring-cookie")
    public String readCookie(
            @CookieValue(name = "THEME_ID", defaultValue = "default-theme_id") String styleId) {
        return styleId;
    }


    @GetMapping("/fetch-cookies")
    public Map<String, String> fetchVariables(@CookieValue(name = "THEME_ID", required = false) String themeId) {
        if (themeId == null) {

            return Collections.emptyMap();
        }

        try {
            Long styleId = Long.parseLong(themeId);
            StyleEntity styleEntity = styleService.getStyleById(styleId);
            return styleEntity != null ? styleService.getSassVariables(styleId) : Collections.emptyMap();
        } catch (NumberFormatException e) {
            return Collections.emptyMap();
        }
    }

}
