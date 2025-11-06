package com.gearfirst.user_be.mail.service;

public interface MailService {
    void sendUserRegistrationMail(String toEmail, String name, String password);

}
