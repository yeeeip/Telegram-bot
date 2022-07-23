package com.nuzhd.controller;

import com.nuzhd.MyBot;
import com.nuzhd.config.ResponsesConfig;
import com.nuzhd.model.Form;
import com.nuzhd.model.FormState;
import com.nuzhd.model.User;
import com.nuzhd.service.MailSender;
import com.nuzhd.service.impl.FormServiceImpl;
import com.nuzhd.service.impl.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolationException;
import java.util.List;

@Component
public class SurveyController {

    private static final Logger log = LoggerFactory.getLogger(MyBot.class);
    private ResponsesConfig responsesConfig;
    private UserServiceImpl userService;

    private MailSender mailSender;
    private FormServiceImpl formService;


    public SurveyController(ResponsesConfig responsesConfig, UserServiceImpl userService, MailSender mailSender, FormServiceImpl formService) {
        this.responsesConfig = responsesConfig;
        this.userService = userService;
        this.mailSender = mailSender;
        this.formService = formService;
    }

    private String formEmailForAdministrator(Form form) {

        String emailText = String.format("""
                        Получена новая анкета от пользователя с Telegram ID %d
                        ID анкеты: %d
                        Имя: %s
                        Фамилия: %s
                        Возраст: %d
                        Email: %s
                        Номер телефона: %s
                        Дополнительная информация: %s
                        """,
                form.getUser().getTelegramChatId(),
                form.getId(),
                form.getFirstName(),
                form.getLastName(),
                form.getAge(),
                form.getEmail(),
                form.getPhoneNumber(),
                form.getAdditionalInfo().equals("0") ? "Отсутствует" : form.getAdditionalInfo());

        return emailText;

    }

    // Checks if user in survey, if yes, deletes last user's poll from the db and returns response, otherwise just returns response
    public String cancelSurvey(User user) {
        if (! userService.getByTelegramChatId(user.getTelegramChatId()).inSurvey()) {
            return responsesConfig.getErrors().get("not-in-survey");
        }

        List<Form> userForms = formService.getAllByUserTelegramId(user.getTelegramChatId());
        Form userLastForm = userForms.get(userForms.size() - 1);
        formService.deleteById(userLastForm.getId());

        user.inSurvey(false);
        userService.save(user);

        return responsesConfig.getCommands().get("cancel");
    }

    // Checks if user can start new survey (whether user has uncompleted surveys)
    // and creates new poll entity in DB connected linked to user
    public boolean startSurvey(User targetUser) {

        List<Form> userForms = formService.getAllByUserTelegramId(targetUser.getTelegramChatId());

        if (! userForms.stream().allMatch(p -> FormState.COMPLETED.equals(p.getState()))) {
            return false;
        }

        Form userForm = new Form();
        userForm.setUser(targetUser);
        formService.save(userForm);

        targetUser.inSurvey(true);
        userService.save(targetUser);

        return true;

    }

    // Checks if user can continue the survey and returns next step message
    public String continueSurvey(User targetUser, String message) {


        List<Form> userForms = formService.getAllByUserTelegramId(targetUser.getTelegramChatId());
        Form userLastForm = userForms.get(userForms.size() - 1);

        try {
            switch (userLastForm.getState()) {
                case null:
                case STARTED:
                    userLastForm.setState(FormState.QUESTION_1);
                    formService.save(userLastForm);
                    return responsesConfig.getQuestions().get("question-1");
                case QUESTION_1:

                    userLastForm.setFirstName(message);

                    userLastForm.setState(FormState.QUESTION_2);

                    formService.validateAndSavePoll(userLastForm);
                    return responsesConfig.getQuestions().get("question-2");
                case QUESTION_2:

                    userLastForm.setLastName(message);

                    userLastForm.setState(FormState.QUESTION_3);
                    formService.validateAndSavePoll(userLastForm);
                    return responsesConfig.getQuestions().get("question-3");
                case QUESTION_3:

                    try {
                        userLastForm.setAge(Integer.parseInt(message));
                    } catch (NumberFormatException e) {
                        return responsesConfig.getErrors().get("invalid-input");
                    }

                    userLastForm.setState(FormState.QUESTION_4);
                    formService.validateAndSavePoll(userLastForm);
                    return responsesConfig.getQuestions().get("question-4");
                case QUESTION_4:

                    userLastForm.setPhoneNumber(message);
                    userLastForm.setState(FormState.QUESTION_5);
                    formService.validateAndSavePoll(userLastForm);
                    return responsesConfig.getQuestions().get("question-5");
                case QUESTION_5:

                    userLastForm.setEmail(message);
                    userLastForm.setState(FormState.QUESTION_6);
                    formService.validateAndSavePoll(userLastForm);

                    return responsesConfig.getQuestions().get("question-6");
                case QUESTION_6:

                    if (! message.equals("0")) {
                        userLastForm.setAdditionalInfo(message);
                    } else {
                        userLastForm.setAdditionalInfo(message);
                    }

                    userLastForm.setState(FormState.COMPLETED);
                    formService.validateAndSavePoll(userLastForm);
                    targetUser.inSurvey(false);
                    userService.save(targetUser);

                    mailSender.sendToAdmin("Новая анкета", formEmailForAdministrator(userLastForm));

                    log.info("##### > Письмо отправлено администратору");

                    return responsesConfig.getQuestions().get("complete");
                default:
                    return responsesConfig.getErrors().get("unknown-error");
            }

        } catch (ConstraintViolationException e) {
            return e.getMessage();
        } catch (MailException e) {
            e.printStackTrace();
            log.error("#### > Не удалось отправить письмо администратору");
            return responsesConfig.getQuestions().get("complete");
        }
    }


}