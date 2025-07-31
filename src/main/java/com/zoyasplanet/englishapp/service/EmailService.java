package com.zoyasplanet.englishapp.service;

public interface EmailService {
    void sendPaymentReminder(String to, String subject, String text);
}
