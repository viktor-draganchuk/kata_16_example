package com.kata.api.exceptions;

/**
 * Checked exception to be thrown by a rule to notify caller about unhandled (perhaps unexpected) conditions during rule execution
 * Generally should terminate payment and make it of failed status.
 * Potentially can be enriched with payload information about exact conditions which led to it.
 *
 * Created by Victor Draganchuk on 28.06.2016.
 */
public class RuleProcessingException extends Exception {
}
