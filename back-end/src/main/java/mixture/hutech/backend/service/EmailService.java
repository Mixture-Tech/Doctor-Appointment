package mixture.hutech.backend.service;

import jakarta.mail.MessagingException;

public interface EmailService {
    void sendMailWithTokenRegister(String toEmail, String name, String token) throws MessagingException;
    void sendMailWithTokenResetPassword(String toEmail, String name, String token) throws MessagingException;
}
