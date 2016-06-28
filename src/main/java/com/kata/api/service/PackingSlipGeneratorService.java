package com.kata.api.service;

import com.kata.api.data.PaymentOrder;

/**
 * This interface should be implemented by a real service (performing slip generation)
 *
 * Created by Victor Draganchuk on 28.06.2016.
 */
public interface PackingSlipGeneratorService {

    void generateSlip(PaymentOrder order);

}
