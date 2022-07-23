package com.nuzhd.repo;

import com.nuzhd.model.Form;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface FormRepository extends JpaRepository<Form, Long> {

    void deleteById(Long id);

    List<Form> getAllByUserTelegramChatId(Long userId);

}
