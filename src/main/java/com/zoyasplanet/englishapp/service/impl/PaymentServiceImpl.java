package com.zoyasplanet.englishapp.service.impl;

import com.zoyasplanet.englishapp.dto.PaymentDTO;
import com.zoyasplanet.englishapp.entity.Payment;
import com.zoyasplanet.englishapp.exception.UserNotFoundException;
import com.zoyasplanet.englishapp.mapper.PaymentMapper;
import com.zoyasplanet.englishapp.repository.PaymentRepository;
import com.zoyasplanet.englishapp.repository.UserRepository;
import com.zoyasplanet.englishapp.service.EmailService;
import com.zoyasplanet.englishapp.service.PaymentService;
import com.zoyasplanet.englishapp.entity.PaymentStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService{

    private static final String PAYMENT_NOT_FOUND_MSG = "Payment not found with id: ";

    private final PaymentRepository paymentRepository;
    private final UserRepository userRepository;
    private final EmailService emailService;

    @Override
    @Transactional
    public PaymentDTO createPayment(PaymentDTO paymentDTO) {
        var user = userRepository.findById(paymentDTO.getUserId())
                .orElseThrow(() -> new UserNotFoundException(paymentDTO.getUserId()));
        var payment = PaymentMapper.INSTANCE.toEntity(paymentDTO);
        payment.setUser(user);
        payment = paymentRepository.save(payment);
        checkAndSendReminder(payment);
        return PaymentMapper.INSTANCE.toDto(payment);
    }

    @Override
    @Transactional
    public PaymentDTO updatePayment(Long id, PaymentDTO paymentDTO) {
        var existingPayment = paymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(PAYMENT_NOT_FOUND_MSG + id));
        PaymentMapper.INSTANCE.updateEntity(existingPayment, paymentDTO);
        existingPayment = paymentRepository.save(existingPayment);
        checkAndSendReminder(existingPayment);
        return PaymentMapper.INSTANCE.toDto(existingPayment);
    }

    @Override
    @Transactional(readOnly = true)
    public PaymentDTO getPaymentById(Long id) {
        return paymentRepository.findById(id)
                .map(PaymentMapper.INSTANCE::toDto)
                .orElseThrow(() -> new RuntimeException(PAYMENT_NOT_FOUND_MSG + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<PaymentDTO> getAllPayments() {
        return paymentRepository.findAll().stream()
                .map(PaymentMapper.INSTANCE::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deletePayment(Long id) {
        paymentRepository.deleteById(id);
    }

    @Override
    public void checkAndSendReminder(Payment payment) {
        LocalDate today = LocalDate.now(); // Реальная дата
        int currentDay = today.getDayOfMonth();
        String userEmail = payment.getUser().getEmail();
        double amount = payment.getAmount();
        LocalDate dueDate = payment.getDueDate();

        if (payment.getStatus() == PaymentStatus.PENDING && currentDay >= 5 && currentDay <= 7) {
            long daysUntilDue = ChronoUnit.DAYS.between(today, dueDate);
            if (currentDay == 5 && daysUntilDue == 2) { // 5-е — за 2 дня
                emailService.sendPaymentReminder(userEmail, "Напоминание об оплате",
                        "Уважаемый(ая) клиент Zoya'sEnglishPlanet, информируем Вас о том, что через 2 дня " +
                                "истечет срок оплаты занятий за текущий месяц, поэтому просим Вас заблаговременно " +
                                "произвести оплату в размере " + amount + " рублей.");
            } else if (currentDay == 6 && daysUntilDue == 1) { // 6-е — за 1 день
                emailService.sendPaymentReminder(userEmail, "Напоминание об оплате",
                        "Уважаемый(ая) клиент Zoya'sEnglishPlanet, информируем Вас о том, что через 1 день " +
                                "истечет срок оплаты занятий за текущий месяц, поэтому просим Вас заблаговременно " +
                                "произвести оплату в размере " + amount + " рублей.");
            } else if (currentDay == 7 && daysUntilDue == 0) { // 7-е — в день оплаты
                emailService.sendPaymentReminder(userEmail, "Срочное напоминание об оплате",
                        "Уважаемый(ая) клиент Zoya'sEnglishPlanet, информируем Вас о том, что сегодня " +
                                "истекает срок оплаты занятий за текущий месяц, в этой связи просим Вас " +
                                "произвести оплату в размере " + amount + " рублей. В противном случае мы " +
                                "не сможем обеспечить Вам дальнейшее взаимодействие с нашими сервисами.");
            }
        }
    }

    @Override
    @Transactional
    public void processPayment(Long paymentId) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new RuntimeException(PAYMENT_NOT_FOUND_MSG + paymentId));
        payment.setStatus(PaymentStatus.PAID);
        payment.setPaidDate(LocalDate.now());
        payment.setDueDate(updateDueDate(payment)); // Прямой вызов, так как не требуется транзакция
        paymentRepository.save(payment);
        checkAndSendReminder(payment);
    }

    @Override
    @Transactional
    public void confirmPayment(Long paymentId, Boolean updateDueDate) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new RuntimeException(PAYMENT_NOT_FOUND_MSG + paymentId));
        payment.setStatus(PaymentStatus.PAID);
        payment.setPaidDate(LocalDate.now());
        if (updateDueDate != null && updateDueDate) { // Проверяем на null и true
            payment.setDueDate(this.updateDueDate(payment));
        }
        paymentRepository.save(payment);
        checkAndSendReminder(payment);
    }

    @Override
    public LocalDate updateDueDate(Payment payment) { // Убрана @Transactional
        return payment.getDueDate().plusMonths(1).withDayOfMonth(7);
    }

}
