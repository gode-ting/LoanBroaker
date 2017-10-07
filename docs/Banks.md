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

**Therefore, it makes sense if we try direct exchange.**

###  Queues

Queues share some properties with exchanges, but also have some additional properties:

* Name
* Durable (the queue will survive a broker restart)
* Exclusive (used by only one connection and the queue will be deleted when that connection closes)
* Auto-delete (queue that has had at least one consumer is deleted when last consumer unsubscribes)
* Arguments (optional; used by plugins and broker-specific features such as message TTL, queue length limit, etc)


Durable queues are persisted to disk and thus survive broker restarts. Queues that are not durable are called transient. Not all scenarios and use cases mandate queues to be durable.

Durability of a queue does not make messages that are routed to that queue durable. If broker is taken down and then brought back up, durable queue will be re-declared during broker startup, however, only persistent messages will be recovered.


### Bindings

Bindings are rules that exchanges use (among other things) to route messages to queues. To instruct an exchange E to route messages to a queue Q, Q has to be bound to E. Bindings may have an optional routing key attribute used by some exchange types. The purpose of the routing key is to select certain messages published to an exchange to be routed to the bound queue. In other words, the routing key acts like a filter.

In our case:

Queue is like your destination is a bank
Exchange is like you are home
Bindings are routes from your home to your destination. There can be zero or many ways to reach it

If an application wants to connect a queue to an exchange, it needs to bind them. The opposite operation is called unbinding.

If a message cannot be routed to any queue (for example, because there are no bindings for the exchange it was published to) it is either dropped or returned to the publisher, depending on message attributes the publisher has set.


### Consumers 

Storing messages in queues is useless unless applications can consume them

wo ways for applications to do this:

Have messages delivered to them ("push API")
Fetch messages as needed ("pull API")

With the "push API", applications have to indicate interest in consuming messages from a particular queue. When they do so, we say that they register a consumer or, simply put, subscribe to a queue.

**This is what we need in our case**

### Message Acknowledgements 

Another important consideration is *message acknowledgements* or simply *ack*

Applications that receive and process messages – may occasionally fail to process individual messages or will sometimes just crash.
There is also the possibility of network issues causing problems. 

This raises a question: when should the broker remove messages from queues?

Two choices:

*After broker sends a message to an application (using either basic.deliver or basic.get-ok AMQP methods).* (automatic acknowledgement model)
*After the application sends back an acknowledgement (using basic.ack AMQP method).* (explicit acknowledgement model)

With the explicit model the application chooses when it is time to send an acknowledgement. It can be right after receiving a message, or after persisting it to a data store before processing,

If a consumer dies without sending an acknowledgement the broker will redeliver it to another consumer or, if none are available at the time, the broker will wait until *at least one consumer* is registered for the same queue before attempting redelivery.








