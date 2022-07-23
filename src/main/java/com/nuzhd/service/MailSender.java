package com.nuzhd.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

@Service
public class MailSender {
    @Autowired
    private JavaMailSenderImpl javaMailSender;
    @Value("${spring.mail.admin}")
    private String adminEmail;
    @Value("${spring.mail.username}")
    private String sender;

    public MailSender() {
    }

    public void sendToAdmin(String subject, String message) throws MailException {
        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setFrom(sender + "@yandex.ru");
        mailMessage.setTo(adminEmail);
        mailMessage.setSubject(subject);
        mailMessage.setText(message);

        javaMailSender.send(mailMessage);
    }

}
