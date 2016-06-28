package com.kata.api.processing;

import com.kata.api.data.PaymentOrder;
import com.kata.api.exceptions.RuleProcessingException;
import com.kata.api.processing.context.PaymentProcessingContextFactory;
import com.kata.api.processing.context.RuleProcessingContext;
import com.kata.api.processing.rules.BusinessRule;
import com.kata.api.processing.rules.RulesProviderFactory;
import com.kata.api.types.RuleProcessResult;
import com.kata.api.types.PaymentProcessResult;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.internal.util.collections.Sets;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Collections;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.*;


/**
 * Created by Victor Draganchuk on 28.06.2016.
 */
@RunWith(MockitoJUnitRunner.class)
public class PaymentProcessorImplTest {

    @Mock
    private RuleProcessingContext context;

    @Mock
    private PaymentOrder order;

    @Mock
    private PaymentProcessingContextFactory paymentProcessingContextFactory;

    @Mock
    private RulesProviderFactory rulesProviderFactory;

    private PaymentProcessorImpl paymentProcessor;

    @Before
    public void initContext() {

        when(context.getOrder()).thenReturn(order);
        when(paymentProcessingContextFactory.paymentProcessingContext(order)).thenReturn(context);

        paymentProcessor = new PaymentProcessorImpl(paymentProcessingContextFactory, rulesProviderFactory);
    }

    @Test
    public void shouldCalledPaymentProcessingContext() {

        when(rulesProviderFactory.getRules()).thenReturn(Collections.EMPTY_SET);
        paymentProcessor.processOrder(order);
        verify(paymentProcessingContextFactory).paymentProcessingContext(order);
    }

    @Test
    public void shouldCalledBusinessRules() {

        when(rulesProviderFactory.getRules()).thenReturn(Collections.EMPTY_SET);
        paymentProcessor.processOrder(order);
        verify(rulesProviderFactory).getRules();
    }

    @Test
    public void shouldSucceedIfNoException() throws RuleProcessingException {

        BusinessRule businessRule = mock(BusinessRule.class);
        when(businessRule.processRule(context)).thenReturn(RuleProcessResult.PERFORMED).thenReturn(RuleProcessResult.IGNORED);
        when(rulesProviderFactory.getRules()).thenReturn(Sets.newSet(businessRule));

        assertEquals(paymentProcessor.processOrder(order), PaymentProcessResult.SUCCESS_PROCESSING);
        verify(businessRule, times(2)).processRule(context);
    }

    @Test
    public void shouldFailIfException() throws RuleProcessingException {

        BusinessRule businessRule = mock(BusinessRule.class);
        doThrow(new RuleProcessingException()).when(businessRule).processRule(context);
        when(rulesProviderFactory.getRules()).thenReturn(Sets.newSet(businessRule));

        assertEquals(paymentProcessor.processOrder(order), PaymentProcessResult.FAILED_PROCESSING);
        verify(businessRule, times(1)).processRule(context);
    }

}
