package com.banking.paymentrequest.dto.request;

import com.banking.paymentrequest.entity.Payment;
import com.banking.paymentrequest.entity.Transaction;

import lombok.Data;

@Data
public class TransactionLogDto {
    private Payment payment;
    private Transaction transaction;
}
