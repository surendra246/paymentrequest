package com.banking.paymentrequest.controller;

import com.banking.paymentrequest.dto.request.PaymentRequestDto;
import com.banking.paymentrequest.dto.response.GenericResponse;
import com.banking.paymentrequest.dto.response.PaymentResponseDto;
import com.banking.paymentrequest.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/create")
    public ResponseEntity<GenericResponse<PaymentResponseDto>> createPaymentLink(
        @RequestBody PaymentRequestDto requestDto,
        @RequestHeader("Authorization") String authHeader
    ) {
        String token = authHeader.startsWith("Bearer ") ? authHeader.substring(7) : authHeader;
        GenericResponse<PaymentResponseDto> response = paymentService.createPaymentRequest(requestDto, token);
        return ResponseEntity.ok(response);
    }
}
