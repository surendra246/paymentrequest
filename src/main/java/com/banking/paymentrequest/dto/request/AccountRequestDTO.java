package com.banking.paymentrequest.dto.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountRequestDTO {

    private Long accountId;
    private Long customerId;

    @NotBlank(message = "Account type is required")
    @Pattern(regexp = "^(Savings|Current|Loan)$", message = "Account type must be Savings, Current, or Loan")
    private String accountType;

    @DecimalMin(value = "0.0", inclusive = false)
    @Digits(integer = 12, fraction = 2)
    private BigDecimal balance;

    private LocalDateTime createdAt;

    // Getters and setters omitted for brevity
}
