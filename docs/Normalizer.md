# Normalizer 

We use a Normalizer to route each message type through a custom Message Translator so that the resulting messages match a common format.

Use case:

1. We recieve a response from every bank which we sent a request to.
2. Every response is in a different format
3. We must route each response through a custom message translator to transform them into a common format.


![alt text](https://github.com/gode-ting/LoanBroaker/blob/master/docs/normalizer.PNG "Normalizer")

