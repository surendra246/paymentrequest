package com.banking.paymentrequest.repository;

import java.util.Optional;

import com.banking.paymentrequest.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import org.springframework.stereotype.Repository;

@Repository
public interface  CustomerRepository extends  JpaRepository<Customer, Long>{
    
    Optional<Customer> findByEmail(String email);
    @Query("SELECT c FROM Customer c WHERE LOWER(c.email) = LOWER(:email) AND c.phone = :phone")
    Optional<Customer> findByEmailAndPhone(@Param("email") String email, @Param("phone") String phone);

    boolean existsByEmail(String email);
}
