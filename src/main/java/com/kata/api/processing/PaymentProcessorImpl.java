package com.kata.api.processing;

import com.kata.api.PaymentProcessor;
import com.kata.api.data.PaymentOrder;
import com.kata.api.exceptions.RuleProcessingException;
import com.kata.api.processing.context.PaymentProcessingContextFactory;
import com.kata.api.processing.context.RuleProcessingContext;
import com.kata.api.processing.rules.BusinessRule;
import com.kata.api.processing.rules.RulesProviderFactory;
import com.kata.api.types.RuleProcessResult;
import com.kata.api.types.PaymentProcessResult;

import java.util.HashSet;
import java.util.Set;

/**
 * This implementation of PaymentProcessor handles order using iterations by collection of BusinessRule.
 * The general idea is:
 * 1. All business rules are implemented in separate classes (which implement BusinessRule)
 * 2. All business rules can communicate with each others via so-called events (using context), when a rule can add own event or/and check events added by other rules
 * 3. All other components (except Context) are stateless
 * 4. PaymentProcessor is single entry point for payment processing (PaymentProcessor.processOrder(PaymentOrder))
 * 5. PaymentProcessorImpl implementation works like this:
 *      a. On  processOrder call it uses injected factories to get all available rules implementations and to generate context
 *      b. It iterates over all available rules (where order does not matter), passes Context to them and retrieves Exception or processing status
 *      c. The iteration can be terminated under two conditions: exception from a rule or no single performed rule during iteration (which means all rules are completed or not applicable)
 *      d. Each rule should be responsible for corresponding business rule logic and also for checking conditions under which it should perform or ignore iteration
 *
 * Created by Victor Draganchuk on 28.06.2016.
 */
public class PaymentProcessorImpl implements PaymentProcessor {

    private final PaymentProcessingContextFactory paymentProcessingContextFactory;
    private final RulesProviderFactory rulesProviderFactory;

    /**
     * Constructor
     * @param paymentProcessingContextFactory - processing context provider
     * @param rulesProviderFactory - available rules provider
     */
    public PaymentProcessorImpl(PaymentProcessingContextFactory paymentProcessingContextFactory, RulesProviderFactory rulesProviderFactory) {
        this.paymentProcessingContextFactory = paymentProcessingContextFactory;
        this.rulesProviderFactory = rulesProviderFactory;
    }

    @Override
    public PaymentProcessResult processOrder(PaymentOrder order) {
        RuleProcessingContext ctx = paymentProcessingContextFactory.paymentProcessingContext(order);

        try {
            while (doRulesIteration(ctx)) ;
        } catch (RuleProcessingException e) {
            // TODO logging must be added here
            System.err.println(e);
            return PaymentProcessResult.FAILED_PROCESSING;
        }

        return PaymentProcessResult.SUCCESS_PROCESSING;
    }

    /**
     * Performs single iteration over all available rules, until the end or one of the rules throws exception
     *
     * @param context - execution context for given iteration
     * @return true - if any of the rules performed an operation on order. false - if all rules ignored this iteration
     * @throws RuleProcessingException if there was irrecoverable conditions during rule processing
     */
    private boolean doRulesIteration(RuleProcessingContext context) throws RuleProcessingException {
        Set<RuleProcessResult> processingStates = new HashSet<RuleProcessResult>();
        for (BusinessRule businessRule : rulesProviderFactory.getRules()) {
            processingStates.add(businessRule.processRule(context));
        }

        return processingStates.contains(RuleProcessResult.PERFORMED);
    }
}
