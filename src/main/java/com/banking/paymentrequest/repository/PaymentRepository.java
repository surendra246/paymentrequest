package com.banking.paymentrequest.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.banking.paymentrequest.entity.Payment;

@Repository
public interface  PaymentRepository extends JpaRepository<Payment, UUID> {

}
