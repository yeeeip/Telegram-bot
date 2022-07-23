package com.nuzhd.config;

import com.nuzhd.util.CommandResponsesLoader;
import com.nuzhd.util.ErrorsTextLoader;
import com.nuzhd.util.QuestionsTextLoader;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

@Configuration
public class ResponsesConfig {

    // Contains commands and responses
    private final HashMap<String, String> commands;
    // Contains possible errors and responses
    private final HashMap<String, String> errors;
    // Contains text of each question
    private final HashMap<String, String> questions;
    private final CommandResponsesLoader commandsLoader;
    private final QuestionsTextLoader questionsLoader;
    private final ErrorsTextLoader errorsLoader;

    public ResponsesConfig(CommandResponsesLoader commandsLoader, ErrorsTextLoader errorsLoader, QuestionsTextLoader questionsLoader) {
        this.errorsLoader = errorsLoader;
        this.commandsLoader = commandsLoader;
        this.questionsLoader = questionsLoader;

        this.commands = commandsLoader.getCommandResponses();
        this.questions = questionsLoader.getQuestionsText();
        this.errors = errorsLoader.getErrorsText();
    }

    public HashMap<String, String> getCommands() {
        return commands;
    }

    public HashMap<String, String> getErrors() {
        return errors;
    }

    public HashMap<String, String> getQuestions() {
        return questions;
    }

}
