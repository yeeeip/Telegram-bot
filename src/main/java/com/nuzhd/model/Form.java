package com.nuzhd.model;

import javax.persistence.*;
import javax.validation.constraints.*;

import static javax.persistence.GenerationType.AUTO;

@Entity
@Table(name = "forms")
public class Form {

    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;

    @Size(min = 2, max = 200, message = "Длина имени должна быть от 2 до 100 символов")
    private String firstName;

    @Size(min = 2, max = 100, message = "Длина фамилии должна быть от 2 до 100 символов")
    private String lastName;
    @Email(message = "Введён некорректный email")
    private String email;

    @Min(value = 12, message = "Минимальный возраст - 12 лет")
    @Max(value = 100, message = "Максимальный возраст - 100 лет")
    private Integer age;

    @Pattern(regexp = "[\\d]{11}", message = "Номер телефона должен состоять из цифр и содержать ровно 11 символов")
    private String phoneNumber;

    @Size(max = 300, message = "Не более 300 символов")
    private String additionalInfo;
    @Enumerated(value = EnumType.STRING)
    private FormState state;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_telegram_chat_id", referencedColumnName = "telegramChatId")
    private User user;

    public Form() {
        this.state = FormState.STARTED;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    public FormState getState() {
        return state;
    }

    public void setState(FormState state) {
        this.state = state;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Form{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", age=" + age +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", additionalInfo='" + additionalInfo + '\'' +
                ", state=" + state +
                ", user=" + user +
                '}';
    }
}
