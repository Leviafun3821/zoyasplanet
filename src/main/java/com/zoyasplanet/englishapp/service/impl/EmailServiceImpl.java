package com.zoyasplanet.englishapp.service.impl;

import com.sendgrid.*;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import com.zoyasplanet.englishapp.exception.EmailSendException;
import com.zoyasplanet.englishapp.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailServiceImpl implements EmailService {

    @Value("${spring.mail.password}")
    private String sendGridApiKey; // Это будет SG.6jZIYV8UTnKiA-...

    @Override
    public void sendPaymentReminder(String to, String subject, String text) {
        try {
            Email from = new Email("napominator.zoya@mail.ru");
            Email toEmail = new Email(to);
            Content content = new Content("text/plain", text);
            Mail mail = new Mail(from, subject, toEmail, content);

            SendGrid sg = new SendGrid(sendGridApiKey);
            Request request = new Request();
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());

            Response response = sg.api(request);

            if (response.getStatusCode() >= 200 && response.getStatusCode() < 300) {
                log.info("Письмо успешно отправлено на: {}", to);
            } else {
                log.error("Ошибка SendGrid: {} {}", response.getStatusCode(), response.getBody());
                throw new EmailSendException("SendGrid вернул ошибку: " + response.getStatusCode());
            }
        } catch (Exception e) {
            log.error("Критическая ошибка при отправке письма на {}: {}", to, e.getMessage());
            throw new EmailSendException("Не удалось отправить письмо на " + to, e);
        }
    }

}
