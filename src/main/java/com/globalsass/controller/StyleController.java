package com.globalsass.controller;

import com.globalsass.service.StyleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/rest/styles")
public class StyleController {

    @Autowired
    private StyleService styleService;

    @GetMapping("/id/{id}")
    public ResponseEntity<Map<String, String>> getStyleVariables(@PathVariable Long id) {
        Map<String, String> sassVariables = styleService.getSassVariables(id);
        return ResponseEntity.ok(sassVariables);
    }

    /*@GetMapping("/id/{id}")
    public ResponseEntity<Map<String, String>> getStyleVariables(@PathVariable Long id) {

        Map<String, String> sassVariables = styleService.getSassVariables(id);

        return getStyleVariablesAsCss(sassVariables);
    }*/

    @GetMapping("/by-cookie")
    public ResponseEntity<String> getStyleVariables(@CookieValue(name = "THEME_ID") String id) {
        Map<String, String> sassVariables = styleService.getSassVariables(Long.valueOf(id));
        return getStyleVariablesAsCss(sassVariables);
    }

    @GetMapping(value = "/css/{id}", produces = "text/css")
    public ResponseEntity<String> getStyleVariablesAsCss(@PathVariable Long id) {
        Map<String, String> sassVariables = styleService.getSassVariables(id);
        return getStyleVariablesAsCss(sassVariables);
    }

    private ResponseEntity<String> getStyleVariablesAsCss(Map<String, String> sassVariables) {
        String cssVariables = sassVariables.entrySet().stream()
                .map(entry -> "--" + convertToKebabCase(entry.getKey()) + ": " + entry.getValue() + ";")
                .collect(Collectors.joining("\n"));

        String cssOutput = ":root {\n" + cssVariables + "\n}";
        return ResponseEntity.ok(cssOutput);
    }

    private String convertToKebabCase(String input) {
        return input.replaceAll("([a-z])([A-Z]+)", "$1-$2").toLowerCase();
    }

}