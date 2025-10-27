package com.banking.paymentrequest.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.banking.paymentrequest.dto.request.GetPaymentRequestDto;
import com.banking.paymentrequest.dto.request.PaymentRequestDto;
import com.banking.paymentrequest.dto.response.GenericResponse;
import com.banking.paymentrequest.dto.response.PaymentDetailsResponseDTO;
import com.banking.paymentrequest.dto.response.PaymentResponseDto;
import com.banking.paymentrequest.service.PaymentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/initiate")
    public ResponseEntity<GenericResponse<PaymentResponseDto>> createPaymentLink(
        @RequestBody PaymentRequestDto requestDto,
        @RequestHeader("Authorization") String authHeader
    ) {
        String token = authHeader.startsWith("Bearer ") ? authHeader.substring(7) : authHeader;
        GenericResponse<PaymentResponseDto> response = paymentService.createPaymentRequest(requestDto, token);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GenericResponse<PaymentDetailsResponseDTO>> getPayment(@PathVariable String id) {
        GetPaymentRequestDto requestDto = new GetPaymentRequestDto();
        requestDto.setPaymentRequestId(id);

        GenericResponse<PaymentDetailsResponseDTO> response = paymentService.getPaymentRequest(requestDto);
        return ResponseEntity.ok(response);
    }
}
