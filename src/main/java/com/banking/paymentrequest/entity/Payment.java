package com.banking.paymentrequest.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.banking.paymentrequest.enums.PaymentLinkStatus;
import com.banking.paymentrequest.enums.PaymentType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Data
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="payment_id", nullable = false, updatable = false)
    private Long paymentId;

    @Column(name="payment_request_id")
    private String paymentRequestId;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @Column(name="amount")
    @NotNull
    private BigDecimal amount;

    @Column(name="purpose")
    @NotBlank
    private String purpose;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Payment type must not be null")
    @Column(name="payment_type")
    private PaymentType paymentType;

    @Column(name="payment_link")
    private String paymentLink;

    @Enumerated(EnumType.STRING)
    @Column(name="status")
    @NotNull
    private PaymentLinkStatus status;

    @DecimalMin(value = "0.01", message = "Amount must be greater than zero")
    @NotNull(message = "Receivable amount is required")
    @Column(name = "receivable_amount")
    private BigDecimal receivableAmount;

    @NotNull(message = "Created date is required")
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @NotNull(message = "Expiry date is required")
    @Column(name = "expiry_date")
    private LocalDateTime expiryDate;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

}
