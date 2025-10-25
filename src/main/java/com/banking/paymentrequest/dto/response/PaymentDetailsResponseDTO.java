package com.banking.paymentrequest.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.banking.paymentrequest.enums.PaymentLinkStatus;
import com.banking.paymentrequest.enums.PaymentType;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class PaymentDetailsResponseDTO {
    private String paymentRequestId;
    @JsonProperty("customer_id")
    private Long customerId;
    @JsonProperty("name")
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
