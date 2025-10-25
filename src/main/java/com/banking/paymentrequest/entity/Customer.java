package com.banking.paymentrequest.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.banking.paymentrequest.enums.CustomerStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="customer_id")
    private Long id;
    private String name;
    private String email;
    private String phone;
    private String address;
    @Column(name="anual_income")
    private BigDecimal annual_income;

    private CustomerStatus status;
    @Column(name="created_at")
    private LocalDateTime createdAt;
}
