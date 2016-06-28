package com.kata.api.processing.context;

import com.kata.api.data.PaymentOrder;

/**
 * An implementation of this interface creates RuleProcessingContext instance (to be used by PaymentProcessor)
 *
 * Created by Victor Draganchuk on 28.06.2016.
 */
public interface PaymentProcessingContextFactory {

    /**
     * Creates payment execution context
     *
     * @param paymentOrder
     * @return newly created RuleProcessingContext holding reference to the passed order
     */
    RuleProcessingContext paymentProcessingContext(PaymentOrder paymentOrder);

}
