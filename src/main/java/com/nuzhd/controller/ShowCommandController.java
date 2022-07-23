package com.nuzhd.controller;

import com.nuzhd.config.ResponsesConfig;
import com.nuzhd.model.Form;
import com.nuzhd.model.User;
import com.nuzhd.service.impl.FormServiceImpl;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ShowCommandController {

    private ResponsesConfig responsesConfig;
    private FormServiceImpl pollService;

    public ShowCommandController(ResponsesConfig responsesConfig, FormServiceImpl pollService) {
        this.responsesConfig = responsesConfig;
        this.pollService = pollService;
    }

    public String fetchPoll(User user, Long pollId) {

        StringBuilder reply = new StringBuilder();

        Form userForm = pollService.getAllByUserTelegramId(user.getTelegramChatId())
                .stream()
                .filter(p -> pollId.equals(p.getId()))
                .findFirst()
                .orElse(null);

        if (userForm == null) {
            reply.append("Заявка с ID " + pollId + " не найдена!");
            return reply.toString();
        }

        reply.append("Заявка №" + pollId + "\n" +
                "Имя: " + userForm.getFirstName() + "\n" +
                "Фамилия: " + userForm.getLastName() + "\n" +
                "Возраст: " + userForm.getAge() + "\n" +
                "Email: " + userForm.getEmail() + "\n" +
                "Номер телефона: " + userForm.getPhoneNumber() + "\n" +
                "Дополнительная информация: " + (userForm.getAdditionalInfo().equals("0") ? "Отсутствует" : userForm.getAdditionalInfo()));

        return reply.toString();

    }

    public String fetchAllPolls(User user) {

        StringBuilder reply = new StringBuilder();
        List<Form> userForms = pollService.getAllByUserTelegramId(user.getTelegramChatId());

        if (userForms.isEmpty()) {
            reply.append(responsesConfig.getErrors().get("no-polls"));
            return reply.toString();
        }

        reply.append("Найдено анкет: " + userForms.size() + "\n");
        userForms.forEach(p -> reply.append("―――――――――――――――\n" +
                "ID: " + p.getId() + "\n" +
                "Имя: " + p.getFirstName() + "\n" +
                "Фамилия: " + p.getLastName() + "\n" +
                "Возраст: " + p.getAge() + "\n" +
                "Email: " + p.getEmail() + "\n" +
                "Номер телефона: " + p.getPhoneNumber() + "\n" +
                "Дополнительная информация: " + (p.getAdditionalInfo().equals("0") ? "Отсутствует" : p.getAdditionalInfo()) + "\n"));

        return reply.toString();
    }
}
