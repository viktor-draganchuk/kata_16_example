package com.kata.api;

import com.kata.api.data.PaymentOrder;
import com.kata.api.types.PaymentProcessResult;

/**
 *  Payment sub-system facade
 *
 * Created by Victor Draganchuk on 28.06.2016.
 */
public interface PaymentProcessor {

    /**
     * Main entry point of payment sub-system
     *
     * @param order
     * @return SUCCESS_PROCESSING if all available rules were succeed or FAILED_PROCESSING if any of rules reported failure
     */
    public PaymentProcessResult processOrder(PaymentOrder order);
}
