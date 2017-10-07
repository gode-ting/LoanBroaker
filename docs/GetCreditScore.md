
## Get Credit Score Component 

Get Credit Score component is a Content Enricher.

It answers this question: 

**How do we communicate with another system if the message originator does not have all the required data items available?**

We use a content enricher to access an external data source in order to augment a message with missing information. In this case we have acces to a Credit Bureau. 

After we retrieve the required data from the resource, we append the data to the message. The original information from the incoming message gets carried over into the resulting message.

### Implementation


