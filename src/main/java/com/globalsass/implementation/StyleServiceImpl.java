package com.globalsass.implementation;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.globalsass.model.StyleEntity;
import com.globalsass.repository.StyleRepository;
import com.globalsass.service.StyleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class StyleServiceImpl implements StyleService {

    private static final Logger logger = LoggerFactory.getLogger(StyleServiceImpl.class);

    @Autowired
    private StyleRepository styleRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public Map<String, String> getSassVariables(Long id) {
        StyleEntity styleEntity = styleRepository.findById(id).orElseThrow(() -> new RuntimeException("Style not found"));
        Map<String, String> sassVariables = new HashMap<>();
        try {
            sassVariables = objectMapper.readValue(styleEntity.getVariables(), new TypeReference<Map<String, String>>() {
            });
        } catch (IOException e) {
            logger.error("Error reading SASS variables", e);
        }
        return sassVariables;
    }

    @Override
    public StyleEntity getStyleById(Long id) {
        return styleRepository.findById(id).orElseThrow(() -> new RuntimeException("Style not found"));
    }

    @Override
    public String getVariablesById(Long id) {
        StyleEntity styleEntity = styleRepository.findById(id).orElseThrow(() -> new RuntimeException("Style not found"));
        return styleEntity.getVariables();
    }
}