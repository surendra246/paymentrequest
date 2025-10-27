package com.banking.paymentrequest.dto.response;

import lombok.Data;

@Data
public class CustomerAccountResponseDTO {
    private Long customerId;
    private Long accountId;
    private String accountNumber;
}
