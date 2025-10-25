package com.banking.paymentrequest.exception;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import com.banking.paymentrequest.constants.ErrorCodes;
import com.banking.paymentrequest.constants.ErrorMessages;
import com.banking.paymentrequest.constants.ResponseMessages;
import com.banking.paymentrequest.dto.response.GenericResponse;

import jakarta.validation.ConstraintViolationException;


@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DuplicateCustomerException.class)
    public ResponseEntity<GenericResponse<List<String>>> handleDuplicateCustomer(DuplicateCustomerException ex) {
        List<String> errors = new ArrayList<>();
        errors.add("customer: " + ResponseMessages.DUPLICATE_CUSTOMER);

        GenericResponse<List<String>> response = new GenericResponse<>(
            ex.getMessage(),
            ErrorCodes.DUPLICATE_CUSTOMER,
            errors,
            ErrorMessages.DUPLICATE_CUSTOMER
        );

        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<GenericResponse<List<String>>> handleValidationErrors(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult()
            .getFieldErrors()
            .stream()
            .map(error -> error.getField() + ": " + error.getDefaultMessage())
            .collect(Collectors.toList());

        GenericResponse<List<String>> response = new GenericResponse<>(
            ErrorMessages.VALIDATION_FAILED,
            ErrorCodes.VALIDATION_FAILED,
            errors,
            "FAILED"
        );

        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<GenericResponse<List<String>>> handleDataError(DataIntegrityViolationException ex) {
        return ResponseEntity.badRequest().body(new GenericResponse<>(
            "Data integrity violation",
            "5002",
            List.of(ex.getMessage()),
            "FAILED"
        ));
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<GenericResponse<Object>> handleUnauthorized(ResponseStatusException ex) {
        int statusCode = ex.getStatusCode().value();
        HttpStatus status = HttpStatus.valueOf(statusCode);
        GenericResponse<Object> response = new GenericResponse<>(
            ex.getReason(), 
            String.valueOf(status.value()), 
            null,     
            "FAILURE"
        );
        return ResponseEntity.status(status).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<GenericResponse<List<String>>> handleGenericException(Exception ex) {
        List<String> errors = new ArrayList<>();
        errors.add(ex.getMessage() != null ? ex.getMessage() : "Unknown error");

        GenericResponse<List<String>> response = new GenericResponse<>(
            "An unexpected error occurred",
            "40004",
            errors,
            "FAILED"
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<GenericResponse<List<String>>> handleConstraintViolation(ConstraintViolationException ex) {
        List<String> errors = ex.getConstraintViolations()
            .stream()
            .map(violation -> violation.getPropertyPath() + ": " + violation.getMessage())
            .collect(Collectors.toList());

        GenericResponse<List<String>> response = new GenericResponse<>(
            "Validation failed",
            "40002",
            errors,
            "FAILED"
        );

        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<GenericResponse<List<String>>> handleJsonParseError(HttpMessageNotReadableException ex) {
        List<String> errors = new ArrayList<>();

        String message = ex.getMostSpecificCause().getMessage();
        if (message != null && message.contains("Cannot coerce empty String")) {
            errors.add("Invalid value for PaymentType: empty string is not allowed");
        } else {
            errors.add("Malformed JSON request: " + message);
        }

        GenericResponse<List<String>> response = new GenericResponse<>(
            "Invalid request format",
            "40003",
            errors,
            "FAILED"
        );

        return ResponseEntity.badRequest().body(response);
    }
}

