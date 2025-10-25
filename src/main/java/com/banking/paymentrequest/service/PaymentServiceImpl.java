package com.banking.paymentrequest.service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.time.LocalDateTime;

import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.banking.paymentrequest.constants.ResponseMessages;
import com.banking.paymentrequest.dto.request.GetPaymentRequestDto;
import com.banking.paymentrequest.dto.request.PaymentRequestDto;
import com.banking.paymentrequest.dto.response.GenericResponse;
import com.banking.paymentrequest.dto.response.PaymentDetailsResponseDTO;
import com.banking.paymentrequest.dto.response.PaymentResponseDto;
import com.banking.paymentrequest.entity.Customer;
import com.banking.paymentrequest.entity.Payment;
import com.banking.paymentrequest.enums.PaymentLinkStatus;
import com.banking.paymentrequest.enums.PaymentType;
import com.banking.paymentrequest.repository.CustomerRepository;
import com.banking.paymentrequest.repository.PaymentRepository;
import com.banking.paymentrequest.security.JwtUtil;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final CustomerRepository customerRepository;
    private final JwtUtil jwtUtil;

    @Override
    @Transactional
    public GenericResponse<PaymentResponseDto> createPaymentRequest(PaymentRequestDto requestDto, String token) {
        // implementation logic here
        Claims claims = extractClaimsOrThrow(token);
        String email = claims.get("email", String.class).trim();
        Customer customer = customerRepository.findByEmail(email)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Customer not found"));

        System.out.println("Customer response"+customer.toString());
        Payment payment = new Payment();
        BigDecimal receivableAmount = (requestDto.getPaymentType() == PaymentType.FIXED)
        ? requestDto.getAmount()
        : requestDto.getReceivableAmount();
        BeanUtils.copyProperties(requestDto,payment);
        payment.setReceivableAmount(receivableAmount);
        payment.setCustomer(customer);
        payment.setExpiryDate(LocalDateTime.now().plusDays(15));
        payment.setStatus(PaymentLinkStatus.OPEN);
        Payment savedPayment = paymentRepository.save(payment);

        // Genrate payment random id
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[24];
        random.nextBytes(bytes);
        String paymentRequestId = Base62Util.encode(bytes);
        String paymentLink = "http://localhost:8080/api/payments/" + paymentRequestId;

        // after insertion
        savedPayment.setPaymentRequestId(paymentRequestId);
        savedPayment.setPaymentLink(paymentLink);
        paymentRepository.save(savedPayment);

        // Prepare response

        PaymentResponseDto responseDto = new PaymentResponseDto();
        responseDto.setPaymentRequestId(paymentRequestId);
        responseDto.setPaymentlinks(paymentLink);
        responseDto.setMessage(ResponseMessages.PAYMENT_CREATED_SUCCESS);
        return new GenericResponse<>("Created", "200", responseDto, "SUCCESS");
    }

    @Override
    public GenericResponse<PaymentDetailsResponseDTO> getPaymentRequest(GetPaymentRequestDto requestDTO) {
        String paymentRequestId = requestDTO.getPaymentRequestId();
        Payment payment = paymentRepository.findByPaymentRequestId(paymentRequestId)
            .stream()
            .findFirst()
            .orElseThrow(() -> new ResourceNotFoundException("Payment not found for ID: " + paymentRequestId));

        Long customerId = payment.getCustomer().getId();
        Customer customer = customerRepository.findById(customerId)
            .orElseThrow(() -> new ResourceNotFoundException("Customer not found for ID: " + customerId));

        PaymentDetailsResponseDTO responseDTO = buildPaymentDetailsResponse(payment, customer);

        return new GenericResponse<>(
            "Payment retrieved successfully",
            "20000",
            responseDTO,
            "SUCCESS"
        );
    }

    private PaymentDetailsResponseDTO buildPaymentDetailsResponse(Payment payment, Customer customer) {
        PaymentDetailsResponseDTO dto = new PaymentDetailsResponseDTO();
        BeanUtils.copyProperties(payment, dto);
        dto.setCustomerId(customer.getId());
        dto.setCustomerName(customer.getName());
        return dto;
    }




    public Claims extractClaimsOrThrow(String token) {
        try {
            return jwtUtil.validateToken(token);
        } catch (JwtException | IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid or expired token");
        }
    }

    public class Base62Util {
        private static final String BASE62 = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

        public static String encode(byte[] input) {
            BigInteger value = new BigInteger(1, input);
            StringBuilder sb = new StringBuilder();
            while (value.compareTo(BigInteger.ZERO) > 0) {
                int remainder = value.mod(BigInteger.valueOf(62)).intValue();
                sb.append(BASE62.charAt(remainder));
                value = value.divide(BigInteger.valueOf(62));
            }
            return sb.reverse().toString();
        }
    }

}
