package com.banking.paymentrequest.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import com.banking.paymentrequest.enums.PaymentLinkStatus;
import com.banking.paymentrequest.enums.PaymentType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="payment_id")
    private UUID paymentId;

    @Column(name="payment_request_id")
    private String paymentRequestId;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @Column(name="amount")
    private BigDecimal amount;

    @Column(name="purpose")
    private String purpose;

    @Column(name="payment_type")
    private PaymentType payment_type;

    @Column(name="payment_link")
    private String paymentLink;

    @Column(name="status")
    private PaymentLinkStatus status;

    @Column(name="receivable_amount")
    private BigDecimal receivable_amount;

    @Column(name="created_at")
    private LocalDateTime createdAt;

    @Column(name="expiry_date")
    private LocalDateTime expiryDate;

}
