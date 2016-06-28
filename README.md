# kata_16_example

It's example of one possible solution to implement [Kata16: Business Rules](http://codekata.com/kata/kata16-business-rules/)

The general idea is:

 1. We have many different business rules. Every one implements common interface BusinessRule

 2. All business rules can communicate with each others via so-called events (using context), when a rule can add own event or/and check events added by other rules

 3. Also we have other components (like Context) to provide an iterraction beetwen all members of processing

 4. We have Processor for processing of payment

 5. Processor works like this:
* a. Processor call injected factories to get all available business rules implementations and to generate context
* b. It iterates over all available rules (where order does not matter), passes Context to them and retrieves Exception or processing status
* c. The iteration can be terminated under two conditions: exception from a rule or no single performed rule during iteration (which means all rules are completed or not applicable)
* d. Each rule should be responsible for corresponding business rule logic and also for checking conditions under which it should perform or ignore iteration