package com.nuzhd.service;

import com.nuzhd.model.User;

import java.util.List;

public interface UserService {

    User getByTelegramChatId(Long chatId);
    User getById(Long id);

    List<User> getAllUsers();

    User save(User user);

}
