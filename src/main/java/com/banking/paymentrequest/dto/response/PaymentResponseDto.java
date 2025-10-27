package com.banking.paymentrequest.dto.response;

import lombok.Data;

@Data
public class PaymentResponseDto {
    private String paymentRequestId;
    private String paymentlinks;
    private String message;
}
