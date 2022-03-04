package com.example.emtsem.service_or_business;

import com.example.emtsem.model.dto.ChargeRequest;
import com.stripe.exception.*;
import com.stripe.model.Charge;

public interface PaymentService {
    Charge pay(ChargeRequest chargeRequest) throws CardException, APIException, AuthenticationException, InvalidRequestException, APIConnectionException;
}
