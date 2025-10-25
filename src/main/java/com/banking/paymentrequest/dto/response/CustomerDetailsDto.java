package com.banking.paymentrequest.dto.request;

import lombok.Data;

@Data
public class CustomerDetailsDto {
    private Long customerId;
    private String name;
    private String email;
    private String phone;
}
