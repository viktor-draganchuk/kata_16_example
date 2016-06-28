package com.kata.api.processing.rules;

import com.kata.api.exceptions.RuleProcessingException;
import com.kata.api.processing.context.RuleProcessingContext;
import com.kata.api.types.RuleProcessResult;

/**
 * Every business rule should implement this interface.
 * Basic procedure for a rule could be:
 *  - check order type
 *  - check existing events in RuleProcessingContext
 *  - make decision: if any actions to be performed or not under the given conditions
 *  - perform actions if needed
 *  - produce event (note: rules can not communicate directly to each other, the only way to exchange info between them - are events)
 *  - return processing status to the caller - if any activities were performed or not (so caller can know if any other iterations are needed or not).
 *
 * Created by Victor Draganchuk on 28.06.2016.
 */
public interface BusinessRule {

    /**
     * Business rule entry point (should not be called directly by clients, designed to be used from PaymentProcessor)
     *
     * @param context - execution context, which provides access for rules to the order and events
     * @return status of execution: IGNORED if no action were performed (due to any reason), otherwise - PERFORMED
     * @throws RuleProcessingException if any (expected or unexpected) conditions occurs - which did not allow the rule to perform operation successfully
     */
    public RuleProcessResult processRule(RuleProcessingContext context) throws RuleProcessingException;

}
