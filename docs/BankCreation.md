## Bank Creations

In addition to implementing the Loan Broker component, we have created two more banks: One bank as a SOAP web service and one bank using messaging (via
RabbitMQ).


### TingGod Bank

The TingGod Bank is using messaging (RabbitMQ). Implementation language is Java.

It can be accessed at exchange loanbroker.tinggod.bankJSON. The url is http://datdb.cphbusiness.dk:15672.

Here is an example of its loan
request format:

```shell
 
 {"ssn":1605789787,"creditScore":598,"loanAmount":10.0,"loanDuration":360}
 
```

The loan duration corresponds to the requested number of months for the loan.
The corresponding response will look like this:

```shell
 
 {"ssn":1605789787,"creditScore":598,"loanAmount":10.0,"loanDuration":360}
 
```
The bank puts reply on the queue which is specified as reply-to in the header.

## Implementation
