
## Get Credit Score Component 

Get Credit Score component is a Content Enricher.

It answers this question: 

**How do we communicate with another system if the message originator does not have all the required data items available?**

We use a content enricher to access an external data source in order to augment a message with missing information. In this case we have acces to a Credit Bureau. 

After we retrieve the required data from the resource, we append the data to the message. The original information from the incoming message gets carried over into the resulting message.

So outlined : 

Consume from Loan Request -> Call Credit Bureau -> Enrich the message -> Produce result message.

### Implementation

**Connection:**

All of our connection settings are in a class called EndPoint. 

It has a constructor that takes an endpoint name as parameter.


```java
ConnectionFactory factory = new ConnectionFactory();
factory.setHost(HOST);
factory.setUsername(USERNAME);
factory.setPassword(PASSWORD);
//getting a connection
Connection connection = factory.newConnection();

//creating a channel
Channel channel = connection.createChannel();

//declaring a queue for this channel. If queue does not exist,
//it will be created on the server.
channel.queueDeclare(endpointName, false, false, false, null);
```
The connection abstracts the socket connection, and takes care of protocol version negotiation and authentication and so on for us. Here we connect to a broker on a machine given by school. If we wanted to connect to a broker on our own machine we'd simply specify its name as *localhost*

To send, we must declare a queue for us to send to; then we can publish a message to the queue. We are using endpoint name as queue name. 

NOTE: **Declaring a queue is idempotent - it will only be created if it doesn't exist already. The message content is a byte array**

Last we have a method for closing the channel and connection:

```java
/**
     * Close channel and connection. Not necessary as it happens implicitly any way. 
     * @throws IOException
     */
     public void close() throws IOException, TimeoutException{
         this.channel.close();
         this.connection.close();
     }
```
*Although it is not necessary as it happens implicitly any way.*



