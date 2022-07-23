package com.nuzhd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@SpringBootApplication
public class DialogBotApplication {

    public static void main(String[] args) throws TelegramApiException {
        SpringApplication.run(DialogBotApplication.class, args);

    }

}
