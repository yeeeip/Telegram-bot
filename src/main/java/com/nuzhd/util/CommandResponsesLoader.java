package com.nuzhd.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class CommandResponsesLoader implements ResponsesFromYamlLoader {

    private final String configFileName;

    public CommandResponsesLoader(@Value("${config.commands}") String configFileName) {
        this.configFileName = configFileName;
    }

    public HashMap<String, String> getCommandResponses() {
        return loadResponsesFromYaml(configFileName);
    }

}
