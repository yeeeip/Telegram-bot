package com.nuzhd.util;

import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

public interface ResponsesFromYamlLoader {

    default HashMap<String, String> loadResponsesFromYaml(String configFileName) {

        Yaml yaml = new Yaml();
        HashMap<String, String> responses = new HashMap<>();

        try (InputStream in = this.getClass().getClassLoader().getResourceAsStream(configFileName)) {
            responses = yaml.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return responses;
    }

}
