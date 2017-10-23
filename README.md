# LoanBroaker

We use github issues and waffle to organise and structure our project. You can find our waffle board here:

[![Waffle.io - Columns and their card count](https://badge.waffle.io/gode-ting/LoanBroaker.svg?columns=all)](http://waffle.io/gode-ting/LoanBroaker)

Repository for System integration Loan broker project on Cphbusiness, PBA in software development.

**Table of contents.**

- [Screen dumps of process flow](#screen-dumps-of-process-flow)
- [Diagrams](#diagrams)
- [Bottle necks](#bottle-necks)
- [How testable is our solution](#how-testable-is-our-solution)
- [Client/Webservice](#clientwebservice)
- [How to run the project](#how-to-run-the-project)
- [Authors](#authors)
- [References](#references)
- [Banks](#banks)
- [Translators](#translators)

## Screen dumps of process flow

### 1. Use the rest service to post a request.

![rest POST post](https://github.com/gode-ting/LoanBroaker/blob/master/Resources/client-post-postman.PNG)

![rest POST server response](https://github.com/gode-ting/LoanBroaker/blob/master/Resources/client-get-response.PNG)

### 2. The getCreditScore process
![getCreditScore output](https://github.com/gode-ting/LoanBroaker/blob/master/Resources/Screen%20dumps/getCreditScore.PNG)

The getCreditScore process recieves the message, then retrieves the creditScore for the given SSN and adds it to the message, then proceeds to send the message to the getBanks process.

### 3. The getBanks process
![getBanks output](https://github.com/gode-ting/LoanBroaker/blob/master/Resources/Screen%20dumps/getBanks.PNG)

After recieving the message, the getBanks process goes to our ruleBase SOAP application and retrieves a list of the banks to which the application should be send, depending on the given creditScore. It then adds said list to the message and sends it to the recipientList process

The getBanks process also sends the message to the aggregator.

### 4. The recipientList process
![recipientList output](https://github.com/gode-ting/LoanBroaker/blob/master/Resources/Screen%20dumps/Recipientlist.PNG)

The recipientLis process loops for each element in the list of banks within the message, and for each loop it sends the application part of the message to all the translators matching the banks.

### 5.1. The XML bank Translator process
![getCreditScore output](https://github.com/gode-ting/LoanBroaker/blob/master/Resources/Screen%20dumps/translatorXML.PNG)

This translator translates the message to XML banks format. Then sends the message to the XML bank.

### 5.2 The Soap bank translator process
![Soap bank output](https://github.com/gode-ting/LoanBroaker/blob/master/Resources/Screen%20dumps/TranslatorSoap.PNG)

This translator translates the message to soap banks format. Then sends the message to the getWebService process.

### 5.3 The getWebService process
![getWebService output](https://github.com/gode-ting/LoanBroaker/blob/master/Resources/Screen%20dumps/GetWebService.PNG)

The getWebService process recieves the message from the matching translator and retrieves the interestRate from the SOAP application and sends it with the SSN to the normalizer.

### 5.4 Our JSONtranslator / CPHBusiness JSONtranslator
![JSONtranslator / CPHBusiness JSONtranslator output](https://github.com/gode-ting/LoanBroaker/blob/master/Resources/translator-json.PNG)

This is the output of 2 process. One called "[Cphbusiness]" and one called "[Gode Ting]".
these translators translates the message to a format our Bank understand and a format CPHBusines bank understand. Then sends the messages to the banks.

### 6. Our JSON bank
![Our JSON bank output](https://github.com/gode-ting/LoanBroaker/blob/master/Resources/Screen%20dumps/TingGodRabbitMQBank.PNG)

This process is our JSON bank. it recieves a message from its matching translator, and finds the interestRate that fits the creditScore. it then sends the message to the normalizer.

### 7. The normalizer process
![normalizer output](https://github.com/gode-ting/LoanBroaker/blob/master/Resources/Screen%20dumps/Normalizer.PNG)

The normalizer process recieves a message from any given bank at any given time, and "normalizes" the message to a format our aggregator knows. then sends the formatted message to the aggregator.

### 8. The Aggregator process
![Aggregator output](https://github.com/gode-ting/LoanBroaker/blob/master/Resources/Screen%20dumps/Aggregator.PNG)

When the aggregator process recieves a message it checks from who it was send. If the message was from the getBanks process, it will prepare to recieves a message from each bank in the list of banks within the message (too know when it should find the best result, and send it). If the message was from the normalizer it adds it to the pool of interestRates.
When all the interestRates are there, the aggregator finds the best result and sends it back to the client.

### 9. Use the rest service to get the response

![rest GET client](https://github.com/gode-ting/LoanBroaker/blob/master/Resources/client-get-response-server1.PNG)

![rest GET server](https://github.com/gode-ting/LoanBroaker/blob/master/Resources/client-get-response.PNG)

## Diagrams

### Design class diagram

### Sequence diagram

![Text](https://github.com/gode-ting/LoanBroaker/blob/master/Resources/sequence-diagram.jpg)

__Short description__

This sequence diagram shows how data flows between actors/services within the system. The LoanBroker system is shown as a single actor and works like a black box. To understand this diagram you therefor don't need to know how the LoanBroker works internally. Everything is handled asyncronously for optimal performance.

__Actors__

* Consumer / Client

* LoanBroker System (Black box)

* Credit Bureau

* Banks 1, 2, 3 & 4

__Walk through__

Let's take the diagram step by step, by explaing what happends along the way.

1. (Client) requests a quote along with information like `ssn`, `amount`, `duration`.

2. (LoanBroker) receives the quote. Then sends a request to (Credit Bureau) with `ssn` as parameter.

3. (Credit Bureau) receives `ssn` and responds with a `creditScore`.

4. (LoanBroker) receives `creditScore` and then sends the quote to all the banks. 

5. (Banks 1, 2, 3 and 4) eventually sends a quote rate back to the (LoanBroker)

6. (LoanBroker) then sends the best offer back to the (Client) once all banks have sent their quote rates OR if the experation date has been passed.

## Bottle necks

Since the LoanBroker system is only a prototype it might be error prone and contain bottlenecks that causes undesired and/or bad behavior. These bottlenecks excists due to the limited time frame the team had to develop the system, and because funtionality and a working system was weighted higher than error handling. In a real system going into production such bottlenecks would never be allowed. 

Below we will try to highlight some of the potential bottlenecks and highlight possible enhancements to improve performance.

- __Ssn (social security number) larger than 32-bit values causes error__

 If you're born later in the year/month so that your ssn becomes too large for a integer to be (bigger than 32-bit).

- __Allow ONLY one application pr. user at a time.__

  When a quote has been sent by a user, we make sure the user cannot send another quote until the first quote has been responded. This is done by storing the users `ssn`. Currently we store the users `ssn` in code inside the api which means that they will never really be persisted. If the server restarts all data are lost. 

  A good solution would be to actually persist the user data in a database like Redis (key-value store) and then match the `ssn` from the incoming to that data.

- __If ONE service breaks - EVERYTHING breaks.__

  The LoanBroker is build with multiple micro services that may or may not be hosted by the same server. Furthermore, the LoanBroker makes multiple web requests to the Credit Bureau and the Rule Base. In everyone of these connections something potentially bad could happen, and if that happends the whole system i broken. This is because we haven't added any major error handling.

  For instance, if GetBanks (the micro service) for some reason fails the whole chain stops. Nothing gets send out to the banks. The Client will never get his/her quote rate. 

  A solution to this could be to have an excpetion micro service which has one responsibility and that is to receive and handle exceptions. From the previous example, if GetBanks raises an exception. It will contact the exception micro service. The service will then put a message to the Client output channel, saying that something went wrong. 

  This would result in a lot of tangled connections. Every microservice inside LoanBroker would then have a connection to that exception service. That would look pretty bad. 

  A more clean solution would to let the initial Loan Requst handle all errors. So if anyone of the services raises an error the inital Loan Request will respond with an error message and an error code. 

- __Credit Bureau return a -1.__
This is an extension from previous. But if Credit Bureau returns -1 on a users quote, the user is not able to get any loans. This is not handled. If this happends the user will never get anu messages about it.

A solution would of cause be to return with an error message from the initial loan request, saying something like. 'Sorry, you cannot loan any monay'.

## How testable is our solution

Our solution demonstrates how a simple application can be complex once it becomes distributed. 

The increased complexity also means increased risk of defects

Risk of defects are hard reproduce because they depend on specific temporal conditions.

Therefore, *we have isolated the application from the messaging implementation by using interfaces and
implementation classes.*

*Why?*

Because testing a single application is much easier than testing multiple, distributed applications
connected by messaging channels.

Single application allows us to trace through the complete
execution path, we do not need a complex start-up procedure to fire up all components.

**Example:**

We have made an interface for handling Credit Score Service. 

```java
public interface CreditScoreServiceDelegate {
    public void didGetCreditScoreWithOptionalException(HashMap application, Exception ex);
}
```
In that way we can create a mock credit bureau gateway implementation that does not actually
connect to any message queue but rather invokes the specified delegate right inside our

```java
public void getCreditScore(HashMap application) 

```

This mock implementation should contain the same logic as the actual credit
bureau so the remainder of the loan broker is completely unaware.

We have classes and interfaces handling producer and consumer.

### Producer example

```java
 public void sendMessage(Serializable object) {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    channel.basicPublish("", endPointName, null, SerializationUtils.serialize(object));
                    delegate.didProduceMessageWithOptionalException(null);
                } catch (IOException ex) {
                    Logger.getLogger(Producer.class.getName()).log(Level.SEVERE, null, ex);
                    delegate.didProduceMessageWithOptionalException(ex);
                }
            }
        });
        t.run();
    }
```

*How is this testable?*

We create an object:

```java
private ProducerDelegate delegate;
```
that is based on this interface: 

```java
public interface ProducerDelegate {
    public void didProduceMessageWithOptionalException(IOException ex);
}
```
Basically we can create unit tests handling different cases.

We have done same thing regarding translators.

### Translator example

```java
public interface XMLTranslatorInterface {
    String translateXml(JSONObject application);
}

```

### We have made a few tests regarding testing the business logic with unit test cases.

In our Json translator we have made a few tests regarding the business logic. 

We have to make sure that our system handles undefined values correctly.

``` javascript
it('should return a formatted json object, and handle undefined values', () => {
let testJson = {
	creditScore: '750',
	loanAmount: '1000.01',
	loanDuration: '365'
};

let expectedJson = {
	ssn: '',
	creditScore: '750',
	loanAmount: '1000.01',
	loanDuration: '365'
};

let actualJson = translator.getFormattedJson(testJson);

expect(actualJson).to.eql(expectedJson);
});
```

In the example we use Javascript with Chai as assertion library.

### If we had more time

1. Verify the Credit Bureau Operation

The Loan Broker accesses this service to obtain credit scores for
customers requesting a loan quote.

In order to verify the correct operation of the external Credit Bureau service we could send periodic Test Messages("heartbeat") to the service.

Because the Credit Bureau service supports a Return Address it is easy to inject a Test Message without disturbing the existing message flow.

We simply provide a dedicated reply channel for test messages and avoid the need for a separate test message separator.

In order to verify the correct operation of the Credit Bureau service we need a Test Data Generator and a Test Data Verifier. The test data generator creates test data to be sent to the service under test. 

A Credit Bureau test message is very simple; the only field that is required is a social security
number (SSN).

## Client/webservice

### Endpoints

#### `/ - GET`

returns all available endpoints as JSON.

#### `/loanRequest - POST`

Should be sent as a body in the format:

```json
{"ssn": "280492-1111", "loanAmount": 1000, "loanDuration": 3}
```

`ssn`: the ssn of the person making the request. Should be a string.
`loanAmount`: the amount to loan. Should be a number.
`loanDuration`: the duration for the loan in years. Should be a number. 

#### `/loanRequest/ssn - GET`

Will return loans registered for that ssn in JSON.

### How to run the client

To run the client you need to have node installed.

When node is installed, you should install all dependencies - this is done with the command `npm install`.

### How to interact with the client

The client is a REST service where you can post request for loan providers, and get a response to this. See [endpoints](#endpoints).

1. Post a request to the service. This is done by sending a POST to `/loanRequest` with a json body in the format `{"ssn": "180492-1111", "loanAmount": 1000.1, "loanDuration": 3}`.
2. Get a response for the request you made before. This is done by sending a GET request to `/loanRequest/:ssn` where ssn is a parameter, and should be the ssn you used in the post.

It's not possible to use the POST endpoint if you've already made one that you haven't yet reviewed by the GET endpoint.
Also, it's not possible to use the GET endpoint unless you've already made a request by the POST endpoint.

### Endpoints

| Endpoint           | Method | Description                                                          | Paramters | Result               |
|--------------------|--------|----------------------------------------------------------------------|-----------|----------------------|
| `/loanRequest`     | POST   | Posts a request to get the best loan of available providers          |           | *Success* or *error* |
| `/loanRequst/:ssn` | GET    | Request result of the POST endpoint - that is the best loan provider | `ssn`     | Json of the result   |
| `/`                | GET    | Returns an object/list of all available endpoints with descriptions  |           | json                 |

#### What could have been done different/improved on the client

We have implemented an offline map of ssn's that be requested loans for, to allow users only to request loans once before they can do it again. In a real-world project this should have been done differently, as the list will reset every time the servers restarts.

Also, the response to the user should have probably have been delivered differently. It's a bit hard to wait for an answer in the browser, as it has to keep loading untill it receives any messages. Our solution was to make a get-request to an endpoint with the ssn, to retrieve the best provider. A proper solution could have been to send an e-mail to the user with the best loan provider(s).

also, obviously there should have been an actual user interface, and not just some rest endpints - those would be a bit hard to non-programmer users.

## Authors

[Frederik Larsen](https://github.com/lalelarsen)

[Anders Bjergfelt](https://github.com/andersbjergfelt)

[Emil Gräs](https://github.com/emilgras)

[Daniel Hillmann](https://github.com/hilleer)

## References

Original project [description](https://github.com/datsoftlyngby/soft2017fall-system-integration-teaching-material/blob/master/assignments/LoanBrokerProject.pdf).

## How to run the project

This is a list of all nessescary proccesses:

1. Run client (node)
2. Run GetCreditScore (Main)
3. Run GetBanks (Main)
4. Run RecipientList (Main)
5. Run Translator Soap (Main)
6. Run Translator XML (Main)
7. Run Translator JSON (node)
8. Run translator JSON GodeTing (node)
9. Run Nomalizer (Main)
10. Run TingGodRabbitMQBank (Main)

## Banks

List of bank id's:

- `bank-lån-and-spar`
- `bank-jyske-bank`
- `bank-nordea`
- `bank-danske-bank`

## Translators.

### Json translators

Written as small node applications run thats consumer's and producer's asynchronously.

#### How to use them.

In a terminal `cd` in to their directory, and in this directory run `npm install`.

**Production:** in this dir, run `npm run start`. Will start two processes that each start their respective translator in production.

**Development:** In this dir, run `npm run dev`. Will start two proccesses that each start teir respective translator in development mode.

