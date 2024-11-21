package com.santé.users.util;

public interface EmailSender {
    //void sendEmail(String toEmail, String body);
    void sendEmail(String recipient, String subject, String body);
}
