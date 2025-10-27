package com.banking.paymentrequest.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.banking.paymentrequest.entity.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {
    
}
