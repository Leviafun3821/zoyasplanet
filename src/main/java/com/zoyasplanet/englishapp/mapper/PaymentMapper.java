package com.zoyasplanet.englishapp.mapper;

import com.zoyasplanet.englishapp.dto.PaymentDTO;
import com.zoyasplanet.englishapp.entity.Payment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PaymentMapper {

    PaymentMapper INSTANCE = Mappers.getMapper(PaymentMapper.class);

    @Mapping(target = "userId", source = "user.id")
    PaymentDTO toDto(Payment payment);

    @Mapping(target = "user", ignore = true)
    Payment toEntity(PaymentDTO paymentDTO);

    @Mapping(target = "user", ignore = true)
    void updateEntity(@MappingTarget Payment payment, PaymentDTO paymentDTO);

}
