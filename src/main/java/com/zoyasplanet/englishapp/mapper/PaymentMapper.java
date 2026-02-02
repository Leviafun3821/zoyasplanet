package com.zoyasplanet.englishapp.mapper;

import com.zoyasplanet.englishapp.dto.PaymentDTO;
import com.zoyasplanet.englishapp.entity.Adjustment;
import com.zoyasplanet.englishapp.entity.Payment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PaymentMapper {

    PaymentMapper INSTANCE = Mappers.getMapper(PaymentMapper.class);

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "finalAmount", expression = "java(calculateFinalAmount(payment))")
    @Mapping(target = "adjustments", source = "adjustments")
    PaymentDTO toDto(Payment payment);

    default Double calculateFinalAmount(Payment payment) {
        double totalAdjustments = payment.getAdjustments().stream()
                .mapToDouble(Adjustment::getAmount)
                .sum();

        return Math.max(0.0, payment.getAmount() - totalAdjustments);
    }

    @Mapping(target = "user", ignore = true)
    Payment toEntity(PaymentDTO paymentDTO);

    @Mapping(target = "id", ignore = true) // Добавляем, чтобы сохранить id
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "adjustments", ignore = true)
    void updateEntity(@MappingTarget Payment payment, PaymentDTO paymentDTO);

}
