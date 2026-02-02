package com.zoyasplanet.englishapp.repository;

import com.zoyasplanet.englishapp.entity.Adjustment;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface AdjustmentRepository extends BaseRepository<Adjustment, Long> {
    List<Adjustment> findByPaymentId(Long paymentId);
}
