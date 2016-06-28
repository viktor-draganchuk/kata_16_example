package com.kata.api.types;

/**
 * Status of processing order by a rule during an iteration
 * If rule ignored iteration (means no activity can be performed due to improper order type or already produced event) - IGNORED is used
 * If rule performed any activities and / or produced any events - stratus should be PERFORMED
 *
 * Created by Victor Draganchuk on 28.06.2016.
 */
public enum RuleProcessResult {
    IGNORED,
    PERFORMED
}
