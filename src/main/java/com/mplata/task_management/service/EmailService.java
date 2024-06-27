package com.mplata.task_management.service;

import com.mplata.task_management.dto.EmailDTO;
import jakarta.mail.MessagingException;

public interface EmailService {

    void sendMail(EmailDTO emailDTO) throws MessagingException;
    void sendSimpleMessage(String to, String subject, String text);
}
