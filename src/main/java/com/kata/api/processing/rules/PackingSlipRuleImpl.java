package com.kata.api.processing.rules;

import com.kata.api.data.PaymentOrderType;
import com.kata.api.exceptions.RuleProcessingException;
import com.kata.api.processing.context.RuleProcessingContext;
import com.kata.api.service.PackingSlipGeneratorService;
import com.kata.api.types.BusinessRuleEvent;
import com.kata.api.types.RuleProcessResult;

/**
 * Example implementation of a business rule (to demonstrate the concept)
 *
 * If the payment is for a physical product, generate a packing slip for shipping.??
 *
 * Created by Victor Draganchuk on 28.06.2016.
 */
public class PackingSlipRuleImpl implements BusinessRule {

    private final PackingSlipGeneratorService slipGeneratorService;

    /**
     * Constructor
     *
     * @param slipGeneratorService - service which actually generates slips (assumed to be implemented separately)
     */
    public PackingSlipRuleImpl(PackingSlipGeneratorService slipGeneratorService) {
        this.slipGeneratorService = slipGeneratorService;
    }

    @Override
    public RuleProcessResult processRule(RuleProcessingContext context) throws RuleProcessingException {

        RuleProcessResult rv;
        final boolean isOrderTypeAllowSlip = context.getOrder().getOrderType() == PaymentOrderType.BOOK_PAYMENT;
        final boolean isAlreadyGenerated = context.containsBusinessRuleEvent(BusinessRuleEvent.PACKING_SLIP_GENERATED);

        if (isOrderTypeAllowSlip && !isAlreadyGenerated) {
            context.addBusinessRuleEvent(BusinessRuleEvent.PACKING_SLIP_GENERATED);
            slipGeneratorService.generateSlip(context.getOrder());
            rv = RuleProcessResult.PERFORMED;
        } else {
            rv = RuleProcessResult.IGNORED;
        }
        return rv;
    }
}