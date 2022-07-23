package com.nuzhd.service.impl;

import com.nuzhd.model.Form;
import com.nuzhd.repo.FormRepository;
import com.nuzhd.service.FormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.util.List;
import java.util.Set;


@Service
public class FormServiceImpl implements FormService {
    private FormRepository formRepository;

    @Autowired
    private Validator validator;

    @Override
    public Form save(Form form) {
        return formRepository.save(form);
    }

    public FormServiceImpl(FormRepository formRepository) {
        this.formRepository = formRepository;
    }

    @Override
    public Form getById(Long id) {
        return formRepository.getById(id);
    }

    @Override
    public List<Form> getAllByUserTelegramId(Long userId) {
        return formRepository.getAllByUserTelegramChatId(userId);
    }

    @Override
    public void deleteById(Long id) {
        formRepository.deleteById(id);
    }

    public Form validateAndSavePoll(Form form) {

        Set<ConstraintViolation<Form>> violations = validator.validate(form);

        if (! violations.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (ConstraintViolation<Form> v : violations) {
                sb.append(v.getMessage());
            }

            throw new ConstraintViolationException("Введено недопустимое значение: " + sb, violations);
        }

        return save(form);
    }
}
