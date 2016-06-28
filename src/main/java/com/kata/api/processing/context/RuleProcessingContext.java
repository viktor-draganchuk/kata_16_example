package com.kata.api.processing.context;

import com.kata.api.data.PaymentOrder;
import com.kata.api.types.BusinessRuleEvent;

/**
 * This context is needed to:
 * - encapsulate rules execution context: such as order, produced events etc
 * - serves as communication mechanism between different business rules (rules can add events and check them to make decisions)
 *
 * Created by Victor Draganchuk on 28.06.2016.
 */
public interface RuleProcessingContext {

    /**
     * @return related payment order
     */
    public PaymentOrder getOrder();

    /**
     * Checks if given event is already is in context. Normally called by rules during conditions assessment.
     *
     * @param evt event to be checked
     * @return true if given event was found in context
     */
    public boolean containsBusinessRuleEvent(BusinessRuleEvent evt);

    /**
     * Adds (register) even. Normally called by business rules during activities execution.
     * Note: context holder behaves as a Set to avoid duplications of events. So it does not make any difference if this method called once or twice for same event
     *
     * @param evt event to add to context
     */
    public void addBusinessRuleEvent(BusinessRuleEvent evt);
}
