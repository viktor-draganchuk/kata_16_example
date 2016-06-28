package com.kata.api.processing.rules;

import com.kata.api.data.PaymentOrder;
import com.kata.api.data.PaymentOrderType;
import com.kata.api.exceptions.RuleProcessingException;
import com.kata.api.processing.context.RuleProcessingContext;
import com.kata.api.service.PackingSlipGeneratorService;
import com.kata.api.types.BusinessRuleEvent;
import com.kata.api.types.RuleProcessResult;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Victor Draganchuk on 28.06.2016.
 */
@RunWith(MockitoJUnitRunner.class)
public class PackingSlipRuleImplTest {

    @Mock
    private RuleProcessingContext context;

    @Mock
    private PaymentOrder order;

    @Mock
    private PackingSlipGeneratorService slipGeneratorService;

    private PackingSlipRuleImpl packingSlipRule;

    @Before
    public void initContext() {
        when(context.getOrder()).thenReturn(order);
        packingSlipRule = new PackingSlipRuleImpl(slipGeneratorService);
    }

    @Test
    public void shouldCheckIfBusinessRuleWillBePerformed() throws RuleProcessingException {

        when(order.getOrderType()).thenReturn(PaymentOrderType.BOOK_PAYMENT);
        packingSlipRule.processRule(context);
        verify(context).containsBusinessRuleEvent(BusinessRuleEvent.PACKING_SLIP_GENERATED);
    }

    @Test
    public void shouldAddEventIfBusinessRulePerformed() throws RuleProcessingException {

        when(order.getOrderType()).thenReturn(PaymentOrderType.BOOK_PAYMENT);
        packingSlipRule.processRule(context);
        verify(context).addBusinessRuleEvent(BusinessRuleEvent.PACKING_SLIP_GENERATED);
    }

    @Test
    public void shouldReturnPerformedProcessingRuleState() throws RuleProcessingException {

        when(order.getOrderType()).thenReturn(PaymentOrderType.BOOK_PAYMENT);
        assertEquals(packingSlipRule.processRule(context), RuleProcessResult.PERFORMED);
    }

    @Test
    public void shouldReturnIgnoredProcessingRuleStateByExistingEvents() throws RuleProcessingException {

        when(order.getOrderType()).thenReturn(PaymentOrderType.BOOK_PAYMENT);
        when(context.containsBusinessRuleEvent(BusinessRuleEvent.PACKING_SLIP_GENERATED)).thenReturn(true);
        assertEquals(packingSlipRule.processRule(context), RuleProcessResult.IGNORED);
    }

    @Test
    public void shouldReturnIgnoredProcessingRuleStateByOrderType() throws RuleProcessingException {

        when(order.getOrderType()).thenReturn(PaymentOrderType.ANOTHER_PAYMENT);
        when(context.containsBusinessRuleEvent(BusinessRuleEvent.PACKING_SLIP_GENERATED)).thenReturn(false);
        assertEquals(packingSlipRule.processRule(context), RuleProcessResult.IGNORED);
    }

    @Test
    public void shouldCallSlipGeneratorServiceForBookOrderType() throws RuleProcessingException {

        when(order.getOrderType()).thenReturn(PaymentOrderType.BOOK_PAYMENT);
        when(context.containsBusinessRuleEvent(BusinessRuleEvent.PACKING_SLIP_GENERATED)).thenReturn(false);
        packingSlipRule.processRule(context);
        verify(slipGeneratorService).generateSlip(context.getOrder());
    }

    @Test
    public void shouldNotCallSlipGeneratorServiceForAnotherOrderType() throws RuleProcessingException {

        when(order.getOrderType()).thenReturn(PaymentOrderType.ANOTHER_PAYMENT);
        when(context.containsBusinessRuleEvent(BusinessRuleEvent.PACKING_SLIP_GENERATED)).thenReturn(false);
        packingSlipRule.processRule(context);
        verify(slipGeneratorService, never()).generateSlip(context.getOrder());
    }
}
