package com.banking.paymentrequest.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetPaymentRequestDto {

    @NotBlank(message = "Payment request id is required")
    @JsonProperty("payment_request_id")
    private String paymentRequestId;
}
