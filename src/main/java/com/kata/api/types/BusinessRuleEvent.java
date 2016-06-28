package com.kata.api.types;

/**
 * Events to be produced by a rule implementation.
 * Potentially should be split by rules somehow, if there are too many of them
 *
 * Created by Victor Draganchuk on 28.06.2016.
 */
public enum BusinessRuleEvent {

    // Example of an event. It's produced by PackingSlipRuleImpl to tell others that packing slip was generated.
    PACKING_SLIP_GENERATED
}
