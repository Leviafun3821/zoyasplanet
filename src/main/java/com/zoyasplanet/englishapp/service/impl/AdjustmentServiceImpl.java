package com.zoyasplanet.englishapp.service.impl;

import com.zoyasplanet.englishapp.dto.AdjustmentDTO;
import com.zoyasplanet.englishapp.dto.PaymentDTO;
import com.zoyasplanet.englishapp.entity.Adjustment;
import com.zoyasplanet.englishapp.entity.Payment;
import com.zoyasplanet.englishapp.exception.ResourceNotFoundException;
import com.zoyasplanet.englishapp.mapper.PaymentMapper;
import com.zoyasplanet.englishapp.repository.AdjustmentRepository;
import com.zoyasplanet.englishapp.repository.PaymentRepository;
import com.zoyasplanet.englishapp.service.AdjustmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class AdjustmentServiceImpl implements AdjustmentService {

    private final AdjustmentRepository adjustmentRepository;
    private final PaymentRepository paymentRepository;
    private static final PaymentMapper paymentMapper = PaymentMapper.INSTANCE;

    @Override
    @Transactional
    public PaymentDTO addAdjustment(Long paymentId, AdjustmentDTO adjustmentDTO) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found with id: " + paymentId));

        Adjustment adjustment = new Adjustment();
        adjustment.setAmount(adjustmentDTO.getAmount());
        adjustment.setReason(adjustmentDTO.getReason());
        adjustment.setAdjustmentDate(adjustmentDTO.getAdjustmentDate() != null
                ? adjustmentDTO.getAdjustmentDate()
                : LocalDate.now());
        adjustment.setPayment(payment);

        adjustmentRepository.save(adjustment);
        return paymentMapper.toDto(payment);
    }

    @Override
    @Transactional
    public PaymentDTO removeAdjustment(Long adjustmentId) {
        Adjustment adjustment = adjustmentRepository.findById(adjustmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Adjustment not found with id: " + adjustmentId));

        Payment payment = adjustment.getPayment();
        payment.getAdjustments().remove(adjustment);
        adjustmentRepository.delete(adjustment);
        return paymentMapper.toDto(payment);
    }

}
