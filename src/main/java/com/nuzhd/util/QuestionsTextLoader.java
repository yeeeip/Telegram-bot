package com.nuzhd.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class QuestionsTextLoader implements ResponsesFromYamlLoader {

    private final String configFileName;

    public QuestionsTextLoader(@Value("${config.questions}") String configFileName) {
        this.configFileName = configFileName;
    }

    public HashMap<String, String> getQuestionsText() {
        return loadResponsesFromYaml(configFileName);
    }

}
