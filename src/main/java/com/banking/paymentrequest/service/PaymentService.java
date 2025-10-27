package com.banking.paymentrequest.service;

import com.banking.paymentrequest.dto.request.GetPaymentRequestDto;
import com.banking.paymentrequest.dto.request.PaymentRequestDto;
import com.banking.paymentrequest.dto.response.GenericResponse;
import com.banking.paymentrequest.dto.response.PaymentDetailsResponseDTO;
import com.banking.paymentrequest.dto.response.PaymentResponseDto;

public interface PaymentService {

    GenericResponse<PaymentResponseDto> createPaymentRequest(PaymentRequestDto requestDto, String token);

    GenericResponse<PaymentDetailsResponseDTO> getPaymentRequest(GetPaymentRequestDto requestDTO);
    
}
