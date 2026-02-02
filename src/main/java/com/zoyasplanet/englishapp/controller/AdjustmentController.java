package com.zoyasplanet.englishapp.controller;

import com.zoyasplanet.englishapp.dto.AdjustmentDTO;
import com.zoyasplanet.englishapp.dto.PaymentDTO;
import com.zoyasplanet.englishapp.service.AdjustmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/adjustments")
@RequiredArgsConstructor
public class AdjustmentController {

    private final AdjustmentService adjustmentService;

    @PostMapping("/payment/{paymentId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PaymentDTO> addAdjustment(
            @PathVariable Long paymentId,
            @Valid @RequestBody AdjustmentDTO adjustmentDTO) {
        PaymentDTO updatedPayment = adjustmentService.addAdjustment(paymentId, adjustmentDTO);
        return ResponseEntity.ok(updatedPayment);
    }

    @DeleteMapping("/{adjustmentId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PaymentDTO> removeAdjustment(@PathVariable Long adjustmentId) {
        PaymentDTO updatedPayment = adjustmentService.removeAdjustment(adjustmentId);
        return ResponseEntity.ok(updatedPayment);
    }
}
