package com.kata.api.processing.rules;

import java.util.Set;

/**
 * An implementation of this interface should allow to add different business rules (it provides them to PaymentProcessor)
 *
 * Created by Victor Draganchuk on 28.06.2016.
 */
public interface RulesProviderFactory {

    /**
     * Returns rules to be used during payment processing
     *
     * @return unordered set of all available (registered) rules of the entire payment processing sub-system
     */
    Set<BusinessRule> getRules();

}
