package com.banking.paymentrequest.entity;

import java.time.LocalDateTime;

import com.banking.paymentrequest.enums.AccountStatus;
import com.banking.paymentrequest.enums.AccountType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import lombok.Data;

@Entity
@Data
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="account_id")
    private Long acount_id;
    
    @Column(unique = true, nullable = false)
    private String accountNumber;

    @Column(nullable = false)
    private Double currentBalance;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name="account_type")
    private AccountType account_type;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        if (currentBalance == null) currentBalance = 10000.0;
    }
    private AccountStatus status;
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
}
