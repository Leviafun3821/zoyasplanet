package com.zoyasplanet.englishapp.service.impl;

import com.zoyasplanet.englishapp.exception.EmailSendException;
import com.zoyasplanet.englishapp.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    @Override
    public void sendPaymentReminder(String to, String subject, String text) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("napominator.zoya@mail.ru");
            message.setTo(to);
            message.setSubject(subject);
            message.setText(text);
            mailSender.send(message);
            log.info("Письмо успешно отправлено на: {}", to);
        } catch (MailSendException e) {
            log.error("Ошибка отправки письма на {}: недействительный email или SMTP ошибка: {}", to, e.getMessage());
            throw new EmailSendException("Не удалось отправить письмо на " + to + ": " + e.getMessage(), e);
        } catch (Exception e) {
            log.error("Неизвестная ошибка при отправке письма на {}: {}", to, e.getMessage());
            throw new EmailSendException("Критическая ошибка отправки письма на " + to, e);
        }
    }

}
