package com.banking.paymentrequest.dto.response;

import lombok.Data;

@Data
public class AccountDetailsDto {
    private Long accountId;
    private Long customerId;
    private String accountNumber;
    private Double currentBalance;
}
