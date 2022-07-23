package com.nuzhd.controller;

import com.nuzhd.config.ResponsesConfig;
import com.nuzhd.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CommandsController {

    private ShowCommandController showCommandController;
    private ResponsesConfig responsesConfig;
    private SurveyController surveyController;

    public String handleCommand(User sender, String rawCommandText) {
        StringBuilder reply = new StringBuilder();

        String[] processedCommand = rawCommandText.replace("/", "").split(" ");

        String arg = processedCommand.length > 1 ? processedCommand[1] : "";

        switch (processedCommand[0].toLowerCase()) {

            case "newdata" -> reply.append(responsesConfig.getCommands().get("newdata"));

            case "survey" -> {
                if (! surveyController.startSurvey(sender)) {
                    reply.append(responsesConfig.getErrors().get("cant-start-survey"));
                    break;
                }
                reply.append(surveyController.continueSurvey(sender, null));
            }

            case "show" -> {
                if (sender.inSurvey()) {
                    reply.append(responsesConfig.getErrors().get("cant-use-command"));
                    break;
                }
                if (arg.isBlank()) {
                    reply.append(showCommandController.fetchAllPolls(sender));
                } else {
                    try {
                        Long parsedArg = Long.parseLong(arg);
                        reply.append(showCommandController.fetchPoll(sender, parsedArg));
                    } catch (NumberFormatException e) {
                        reply.append(responsesConfig.getErrors().get("invalid-input"));
                    }
                }

            }

            case "help" -> {

                if (arg.isBlank()) {
                    reply.append(responsesConfig.getCommands().get("help"));
                    break;
                }

                switch (arg) {
                    case "start" -> reply.append(responsesConfig.getCommands().get("help-start"));
                    case "newdata" -> reply.append(responsesConfig.getCommands().get("help-newdata"));
                    case "cancel" -> reply.append(responsesConfig.getCommands().get("help-cancel"));
                    case "help" -> reply.append(responsesConfig.getCommands().get("help-help"));
                    case "show" -> reply.append(responsesConfig.getCommands().get("help-show"));
                    default -> reply.append("Справка по данной команде отсутствует");
                }


            }

            case "cancel" -> reply.append(surveyController.cancelSurvey(sender));

            default -> reply.append("Введена неопознанная команда");
        }

        return reply.toString();
    }

    public CommandsController() {
    }

    @Autowired
    public CommandsController(ShowCommandController showCommandController, ResponsesConfig responsesConfig, SurveyController surveyController) {
        this.showCommandController = showCommandController;
        this.responsesConfig = responsesConfig;
        this.surveyController = surveyController;
    }

}
