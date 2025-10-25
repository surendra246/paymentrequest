package com.banking.paymentrequest.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import com.banking.paymentrequest.enums.PaymentLinkStatus;
import com.banking.paymentrequest.enums.PaymentType;

import lombok.Data;

@Data
public class PaymentDetailsResponseDTO {
    private UUID paymentId;
    private Long customerId;
    private String customerName;
    private BigDecimal amount;
    private String purpose;
    private PaymentType paymentType;
    private String paymentLink;
    private PaymentLinkStatus status;
    private BigDecimal receivableAmount;
    private LocalDateTime createdAt;
    private LocalDateTime expiryDate;
}
