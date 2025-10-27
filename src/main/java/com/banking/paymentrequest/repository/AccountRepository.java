package com.banking.paymentrequest.repository;

import com.banking.paymentrequest.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {

}
