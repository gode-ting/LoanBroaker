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

* Add RabbitMQ to your POM file

```java
<dependency>
            <groupId>com.rabbitmq</groupId>
            <artifactId>amqp-client</artifactId>
            <version>4.2.1</version>
</dependency>
```
### Considerations

Checklist:

* Exchange
* Queue 
* Binding
* Consumer
* Message 
* Connections
* Channels


Messaging brokers receive messages from publishers (applications that publish them, also known as producers) and route them to consumers (applications that process them).

Since it is a network protocol, the publishers, consumers and the broker can all reside on different machines.

Networks are unreliable and applications may fail to process messages therefore the AMQP model has a notion of message acknowledgements: when a message is delivered to a consumer the consumer notifies the broker, either automatically or as soon as the application developer chooses to do so


In certain situations, for example, when a message cannot be routed, messages may be returned to publishers, dropped, or, if the broker implements an extension, placed into a so-called "dead letter queue". We can use durable exchanges to handle this.

Durable exchanges survive broker restart whereas transient exchanges do not (they have to be redeclared when broker comes back online). Not all scenarios and use cases require exchanges to be durable.

There are 4 different type of exchanges:


| Name             | Default pre-declared names              |
|------------------|-----------------------------------------|
| Direct exchange  | (Empty string) and amq.direct           |
| Fanout exchange  | amq.fanout                              |
| Topic exchange   | amq.topic                               |
| Headers exchange | amq.match (and amq.headers in RabbitMQ) |

*Which one to use in our case?*

**Direct Exchange**

*A direct exchange delivers messages to queues based on the message routing key. A direct exchange is ideal for the unicast routing of messages (although they can be used for multicast routing as well).*

Here is how it works:

A queue binds to the exchange with a routing key K
When a new message with routing key R arrives at the direct exchange, the exchange routes it to the queue if K = R

Use cases:

* Direct (near real-time) messages to individual players in an MMO game
* Delivering notifications to specific geographic locations (for example, points of sale)
* Distributing tasks between multiple instances of the same application all having the same function, for example, image processors
* Passing data between workflow steps, each having an identifier (also consider using headers exchange)
* Delivering notifications to individual software services in the network

So if both producer and consumer is using same routing key, a direct exchange will happen.

**Fanout Exchange**

*A fanout exchange routes messages to all of the queues that are bound to it and the routing key is ignored. If N queues are bound to a fanout exchange, when a new message is published to that exchange a copy of the message is delivered to all N queues. Fanout exchanges are ideal for the broadcast routing of messages.*

A use cases for a fanout exchange could be a sport news site. (distributing score updates to mobile clients in near real-time)


*So which one to use in our case?*

After we have determined the most appropriate banks to contact from the Rule Base web service we must send a request 
to each selected bank (ssn, credit score, loan amount, loan duration).

Each request is unique and therefore should not have any interest in any other responses than its own.

If it connects to a fanout exchange it gets all other responses. 

If it connects to a direct exchange it gets a responses matching the request. 

Therefore, it makes sense if we try direct exchange. 



