package com.banking.paymentrequest.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CustomerAccountRequestDTO {

    @Valid
    @NotNull(message = "Customer details are required")
    private CustomerRequestDTO customer;

    @Valid
    @NotNull(message = "Account details are required")
    private AccountRequestDTO account;
}
