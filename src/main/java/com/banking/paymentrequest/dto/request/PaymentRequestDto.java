package com.banking.paymentrequest.dto.request;

import java.math.BigDecimal;

import com.banking.paymentrequest.enums.PaymentType;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRequestDto {

    @NotBlank(message = "Amount is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Amount must be positive")
    private BigDecimal amount;

    @NotBlank(message = "Description is required")
    private String purpose;

    @NotBlank(message = "Payment type is required")
    private PaymentType payment_type;

    @NotBlank(message = "Amount is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Amount must be positive")
    private BigDecimal receivable_amount;
    
}
