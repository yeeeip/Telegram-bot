package com.nuzhd;

import com.nuzhd.controller.CommandsController;
import com.nuzhd.controller.MessagesController;
import com.nuzhd.model.User;
import com.nuzhd.service.impl.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class MyBot extends TelegramLongPollingBot {

    private MessagesController messagesController;
    private CommandsController commandsController;
    private UserServiceImpl userService;
    private static final Logger log = LoggerFactory.getLogger(MyBot.class);
    private final String botToken;
    private final String botUsername;

    public MyBot(@Value("${bot.token}") String botToken,
                 @Value("${bot.username}") String botUsername, UserServiceImpl userService, CommandsController commandsController, MessagesController messagesController) {
        this.commandsController = commandsController;
        this.botToken = botToken;
        this.botUsername = botUsername;
        this.userService = userService;
        this.messagesController = messagesController;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (! update.hasMessage() || ! update.getMessage().hasText()) {
            return;
        }

        Long chatId = update.getMessage().getChatId();
        String message = update.getMessage().getText().trim();

        log.info("##### > Id: " + chatId + " Сообщение: " + message);

        User user;

        if (isNewUser(chatId)) {
            user = new User(chatId, false, false);
            userService.save(user);
        } else {
            user = userService.getByTelegramChatId(chatId);
        }


        String reply;
        if (message.startsWith("/")) {
            reply = commandsController.handleCommand(user, message);
        } else {
            reply = messagesController.handleIncomingMessage(user, message);
        }

        sendMessage(reply, chatId);

    }

    private void sendMessage(String message, Long chatId) {

        SendMessage msg = SendMessage
                .builder()
                .text(message)
                .chatId(chatId.toString())
                .build();

        try {
            execute(msg);
        } catch (TelegramApiException e) {
            log.info("Сообщение на отправку: " + message);
            throw new RuntimeException("Не удалось отправить сообщение");
        }
    }


    // If this is a new user, returns true, otherwise false
    private boolean isNewUser(Long chatId) {
        return userService.getAllUsers()
                .stream()
                .noneMatch(u -> chatId.equals(u.getTelegramChatId()));
    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }
}
