package com.nuzhd.service;

import com.nuzhd.model.Form;

import java.util.List;

public interface FormService {

    void deleteById(Long id);

    Form save(Form form);

    Form getById(Long id);

    List<Form> getAllByUserTelegramId(Long userId);

}
