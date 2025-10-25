package com.banking.paymentrequest.dto.request;

import java.math.BigDecimal;

import com.banking.paymentrequest.enums.PaymentType;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRequestDto {

    @NotNull(message = "Amount is required")
    @DecimalMin(value = "0.1", inclusive = false, message = "Amount must be positive")
    private BigDecimal amount;

    @NotBlank(message = "Purpose is required")
    private String purpose;

    @NotNull(message = "Payment type is required")
    @JsonProperty("payment_type")
    private PaymentType paymentType;

    @NotNull(message = "Receivable amount is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Receivable amount must be positive")
    @JsonProperty("receivable_amount")
    private BigDecimal receivableAmount;
}
