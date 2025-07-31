package com.zoyasplanet.englishapp.service;

import com.zoyasplanet.englishapp.dto.PaymentDTO;
import com.zoyasplanet.englishapp.entity.Payment;

import java.util.List;

public interface PaymentService {
    PaymentDTO createPayment(PaymentDTO paymentDTO);
    PaymentDTO getPaymentById(Long id);
    List<PaymentDTO> getAllPayments();
    PaymentDTO updatePayment(Long id, PaymentDTO paymentDTO);
    void deletePayment(Long id);

    // Новый метод для доступа к логике напоминаний
    void checkAndSendReminder(Payment payment);
}
