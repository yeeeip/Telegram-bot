package com.nuzhd.controller;

import com.nuzhd.config.ResponsesConfig;
import com.nuzhd.model.User;
import com.nuzhd.service.impl.UserServiceImpl;
import org.springframework.stereotype.Component;

@Component
public class MessagesController {

    private SurveyController surveyController;
    private ResponsesConfig responsesConfig;
    private UserServiceImpl userService;

    public MessagesController(UserServiceImpl userService, ResponsesConfig responsesConfig, SurveyController surveyController) {
        this.userService = userService;
        this.responsesConfig = responsesConfig;
        this.surveyController = surveyController;
    }

    public String handleIncomingMessage(User sender, String messageText) {
        StringBuilder reply = new StringBuilder();

        if (! userService.getByTelegramChatId(sender.getTelegramChatId()).inSurvey()) {
            reply.append(responsesConfig.getErrors().get("not-in-survey"));
            return reply.toString();
        }

        reply.append(surveyController.continueSurvey(sender, messageText));

        return reply.toString();
    }

}

