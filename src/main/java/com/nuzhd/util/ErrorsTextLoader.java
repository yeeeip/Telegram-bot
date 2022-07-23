package com.nuzhd.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class ErrorsTextLoader implements ResponsesFromYamlLoader {

    private final String configFileName;

    public ErrorsTextLoader(@Value("${config.errors}") String configFileName) {
        this.configFileName = configFileName;
    }
    public HashMap<String, String> getErrorsText() {
        return loadResponsesFromYaml(configFileName);
    }
}
