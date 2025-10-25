package com.banking.paymentrequest.repository;

import com.banking.paymentrequest.entity.CustomerAccountApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerAccountApplicationRepository extends JpaRepository<CustomerAccountApplication, Long> {
}
