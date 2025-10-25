package com.banking.paymentrequest.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.banking.paymentrequest.dto.request.CustomerAccountRequestDTO;
import com.banking.paymentrequest.dto.response.CustomerAccountResponseDTO;
import com.banking.paymentrequest.dto.response.GenericResponse;
import com.banking.paymentrequest.service.AccountCreationService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/bank")
public class AccountCreationController {

    private final AccountCreationService accountCreationService;

    public AccountCreationController(AccountCreationService accountCreationService) {
        this.accountCreationService = accountCreationService;
    }

    @PostMapping("/onboarding")
    public ResponseEntity<GenericResponse<CustomerAccountResponseDTO>> createAccount(
            @Valid @RequestBody CustomerAccountRequestDTO requestDTO) {
        System.out.println(requestDTO);
        GenericResponse<CustomerAccountResponseDTO> response = accountCreationService.createAccount(requestDTO);
        return ResponseEntity.ok(response);
    }
}
