package com.globalsass.controller;

import com.globalsass.service.StyleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/rest/styles")
public class StyleController {

    @Autowired
    private StyleService styleService;

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, String>> getStyleVariables(@PathVariable Long id) {
        Map<String, String> sassVariables = styleService.getSassVariables(id);
        return ResponseEntity.ok(sassVariables);
    }
}