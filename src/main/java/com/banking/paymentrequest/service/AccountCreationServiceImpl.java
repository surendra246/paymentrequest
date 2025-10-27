package com.banking.paymentrequest.service;

import com.banking.paymentrequest.constants.ErrorCodes;
import com.banking.paymentrequest.constants.ErrorMessages;
import com.banking.paymentrequest.constants.ResponseMessages;
import com.banking.paymentrequest.dto.request.CustomerAccountRequestDTO;
import com.banking.paymentrequest.dto.response.CustomerAccountResponseDTO;
import com.banking.paymentrequest.dto.response.GenericResponse;
import com.banking.paymentrequest.entity.Account;
import com.banking.paymentrequest.entity.Customer;
import com.banking.paymentrequest.entity.CustomerAccountApplication;
import com.banking.paymentrequest.repository.AccountRepository;
import com.banking.paymentrequest.repository.CustomerAccountApplicationRepository;
import com.banking.paymentrequest.repository.CustomerRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
public class AccountCreationServiceImpl implements AccountCreationService {

    private final CustomerRepository customerRepository;
    private final AccountRepository accountRepository;
    private final CustomerAccountApplicationRepository customerAccountApplicationRepository;

    public AccountCreationServiceImpl(CustomerRepository customerRepository,
                                      AccountRepository accountRepository,
                                      CustomerAccountApplicationRepository customerAccountApplicationRepository) {
        this.customerRepository = customerRepository;
        this.accountRepository = accountRepository;
        this.customerAccountApplicationRepository = customerAccountApplicationRepository;
    }

    @Override
    @Transactional
    public GenericResponse<CustomerAccountResponseDTO> createAccount(CustomerAccountRequestDTO requestDTO) {
        try {
            // 1️⃣ Validate request
            if (requestDTO == null || requestDTO.getCustomer() == null || requestDTO.getAccount() == null) {
                return new GenericResponse<>(
                        ResponseMessages.INVALID_INPUT,
                        ErrorCodes.DATA_VALIDATION_FAILED,
                        null,
                        "FAILURE"
                );
            }

            String email = requestDTO.getCustomer().getEmail();

            // 2️⃣ Check duplicate customer email
            if (email != null && customerRepository.existsByEmail(email)) {
                return new GenericResponse<>(
                        ErrorMessages.DUPLICATE_CUSTOMER,
                        ErrorCodes.DUPLICATE_CUSTOMER,
                        null,
                        "FAILURE"
                );
            }

            // 3️⃣ Save Customer
            Customer customer = new Customer();
            BeanUtils.copyProperties(requestDTO.getCustomer(), customer);
            customer.setCreatedAt(LocalDateTime.now());
            customer = customerRepository.save(customer);

            // 4️⃣ Save Account
            Account account = new Account();
            BeanUtils.copyProperties(requestDTO.getAccount(), account);
            account.setCustomer(customer);
            account.setAccountNumber(generateAccountNumber());
            account.setCreatedAt(LocalDateTime.now());
            account = accountRepository.save(account);

            // 5️⃣ Save CustomerAccountApplication
            CustomerAccountApplication application = new CustomerAccountApplication();
            application.setCustomer(customer);
            application.setAccount(account);
            application.setCreatedAt(LocalDateTime.now());
            customerAccountApplicationRepository.save(application);

            // 6️⃣ Prepare response DTO
            CustomerAccountResponseDTO responseDTO = new CustomerAccountResponseDTO();
            responseDTO.setCustomerId(customer.getId());
            responseDTO.setAccountId(account.getAcount_id());
            responseDTO.setAccountNumber(account.getAccountNumber());

            return new GenericResponse<>(
                    ResponseMessages.APPLICATION_SUBMITTED,
                    "2001",
                    responseDTO,
                    "SUCCESS"
            );

        } catch (DataIntegrityViolationException | ConstraintViolationException e) {
            return new GenericResponse<>(
                    ResponseMessages.LOAN_APPLICATION_FAILED_DUE_DATA,
                    ErrorCodes.DATA_VALIDATION_FAILED,
                    null,
                    "FAILURE"
            );
        } catch (Exception e) {
            return new GenericResponse<>(
                    ResponseMessages.UNEXPECTED_ERROR,
                    ErrorCodes.UNEXPECTED_ERROR,
                    null,
                    "FAILURE"
            );
        }
    }

    /**
     * Generates a random unique 10-digit account number
     */
    private String generateAccountNumber() {
        Random random = new Random();
        long number = 1000000000L + (long) (random.nextDouble() * 9000000000L);
        return String.valueOf(number);
    }
}
