package com.banking.paymentrequest.service;

import com.banking.paymentrequest.dto.request.CustomerAccountRequestDTO;
import com.banking.paymentrequest.dto.response.CustomerAccountResponseDTO;
import com.banking.paymentrequest.dto.response.GenericResponse;

public interface AccountCreationService {
   GenericResponse<CustomerAccountResponseDTO> createAccount(CustomerAccountRequestDTO requestDTO);
}
