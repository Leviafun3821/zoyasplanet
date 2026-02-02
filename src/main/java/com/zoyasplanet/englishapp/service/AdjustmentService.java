package com.zoyasplanet.englishapp.service;

import com.zoyasplanet.englishapp.dto.AdjustmentDTO;
import com.zoyasplanet.englishapp.dto.PaymentDTO;

public interface AdjustmentService {
    PaymentDTO addAdjustment(Long paymentId, AdjustmentDTO adjustmentDTO);
    PaymentDTO removeAdjustment(Long adjustmentId);
}
