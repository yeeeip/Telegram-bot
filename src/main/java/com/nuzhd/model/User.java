package com.nuzhd.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

import static javax.persistence.GenerationType.AUTO;

@Entity
@Table(name = "users")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;

    private boolean in_survey;
    private Long telegramChatId;
    private boolean isAdmin;

    public User() {
    }

    public User(Long telegramChatId, boolean isAdmin, boolean in_survey) {
        this.telegramChatId = telegramChatId;
        this.isAdmin = isAdmin;
        this.in_survey = in_survey;
    }

    public boolean inSurvey() {
        return in_survey;
    }

    public void inSurvey(boolean in_survey) {
        this.in_survey = in_survey;
    }

    public Long getId() {
        return id;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTelegramChatId() {
        return telegramChatId;
    }

    public void setTelegramChatId(Long telegramChatId) {
        this.telegramChatId = telegramChatId;
    }
}
