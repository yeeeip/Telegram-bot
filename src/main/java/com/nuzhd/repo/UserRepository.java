package com.nuzhd.repo;

import com.nuzhd.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByTelegramChatId(Long chatId);

}
