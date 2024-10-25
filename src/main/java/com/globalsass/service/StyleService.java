package com.globalsass.service;

import com.globalsass.model.StyleEntity;

import java.util.Map;

public interface StyleService {
    Map<String, String> getSassVariables(Long id);

    StyleEntity getStyleById(Long id);

    String getVariablesById(Long id);
}