package com.zoyasplanet.englishapp.repository;

import com.zoyasplanet.englishapp.entity.Payment;
import com.zoyasplanet.englishapp.entity.PaymentStatus;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends BaseRepository<Payment, Long>{
    List<Payment> findByStatus(PaymentStatus status);
    List<Payment> findByUserId(Long userId); // Новый метод
}
