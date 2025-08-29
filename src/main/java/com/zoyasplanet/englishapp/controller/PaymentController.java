package com.zoyasplanet.englishapp.controller;

import com.zoyasplanet.englishapp.dto.PaymentDTO;
import com.zoyasplanet.englishapp.service.PaymentService;
import com.zoyasplanet.englishapp.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;
    private final UserService userService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PaymentDTO> createPayment(@Valid @RequestBody PaymentDTO paymentDTO) {
        PaymentDTO createdPayment = paymentService.createPayment(paymentDTO);
        return ResponseEntity.ok(createdPayment);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentDTO> getPaymentById(@PathVariable Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = auth.getName();
        PaymentDTO paymentDTO = paymentService.getPaymentById(id);
        Long currentUserId = userService.getUserIdByUsername(currentUsername);
        if (!currentUserId.equals(paymentDTO.getUserId()) && auth.getAuthorities().stream()
                .noneMatch(granted -> granted.getAuthority().equals("ROLE_ADMIN"))) {
            throw new AccessDeniedException("Доступ только к своим данным или для администратора");
        }
        return ResponseEntity.ok(paymentDTO);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<PaymentDTO>> getAllPayments() {
        List<PaymentDTO> paymentDTOs = paymentService.getAllPayments();
        return ResponseEntity.ok(paymentDTOs);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PaymentDTO> updatePayment(@PathVariable Long id, @Valid @RequestBody PaymentDTO paymentDTO) {
        PaymentDTO updatedPayment = paymentService.updatePayment(id, paymentDTO);
        return ResponseEntity.ok(updatedPayment);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deletePayment(@PathVariable Long id) {
        paymentService.deletePayment(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/process")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> processPayment(@PathVariable Long id) {
        paymentService.processPayment(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/confirm")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> confirmPayment(@PathVariable Long id, @RequestParam(required = false) Boolean updateDueDate) {
        paymentService.confirmPayment(id, updateDueDate);
        return ResponseEntity.ok().build();
    }

}
