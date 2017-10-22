# LoanBroaker

[![Stories in Ready](https://badge.waffle.io/hilleer/LoanBroaker.png?label=ready&title=Ready)](http://waffle.io/hilleer/LoanBroaker)

Repository for System integration Loan broker project on Cphbusiness, PBA in software development.

**Table of contents.**

- [Important links](#important-links)
- [Authors](#authors)
- [References](#references)
- [Banks](#banks)
- [Translators](#translators)
- [Notes](#notes)

## Important links

## Authors

[Frederik Larsen](https://github.com/lalelarsen)

[Anders Bjergfelt](https://github.com/andersbjergfelt)

[Emil Gräs](https://github.com/emilgras)

[Daniel Hillmann](https://github.com/hilleer)

## References

Original project [description](https://github.com/datsoftlyngby/soft2017fall-system-integration-teaching-material/blob/master/assignments/LoanBrokerProject.pdf).

## Run the project

1. Run GetCreditScore (Main)
2. Run GetBanks (Main)
3. Run RecipientList (Main)
4. Run Translator (Main)
5. Run Nomalizer (Main)
6. Run TingGodRabbitMQBank (Main)
7. Run GetCreditScore (Dummy)

## Banks

List of bank id's:

- `bank-lån-and-spar`
- `bank-jyske-bank`
- `bank-nordea`
- `bank-danske-bank`

## Translators.

**Node translators:**

- TranslatorJson
- TranslatorJsonGodeTing

In this dir run `npm install`.

**Production:** in this dir, run `npm run start`. Will start two processes that each start their respective translator in production.

**Development:** In this dir, run `npm run dev`. Will start two proccesses that each start teir respective translator in development mode.

## Notes

### HashMap vs. JSON

Why did we choose to pass HashMap from process to process, and why did we regret it? 

### Better namings

Could we have named our proccesses better, and how would this possibly have helped us? 

- Structure.
- Which services need what?

### Design class diagram 

### Sequence diagram      

![Text](https://github.com/gode-ting/LoanBroaker/blob/master/Resources/sequence-diagram.jpg =200x100)

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



### Bottle necks

Since the LoanBroker system is only a prototype it might be error prone and contain bottlenecks that causes undesired and/or bad behavior. These bottlenecks excists due to the limited time frame the team had to develop the system, and because funtionality and a working system was weighted higher than error handling. In a real system going into production such bottlenecks would never be allowed. 

Below we will try to highlight some of the potential bottlenecks and highlight possible enhancements to improve performance.

- __Ssn (social security number) larger than 32-bit values causes error__   

 If you're born later in the year/month so that your ssn becomes too large for a integer to be (bigger than 32-bit).


- __Allow ONLY one application pr. user at a time.__

  When a quote has been sent by a user, we make sure the user cannot send another quote until the first quote has been 	      responded. This is done by storing the users `ssn`. Currently we store the users `ssn` in code inside the api which means that they will never really be persisted. If the server restarts all data are lost. 

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


### How testable is our solution?

Our solution demonstrates how a simple application can be complex once it becomes distributed. 

The increased complexity also means increased risk of defects

Risk of defects are hard reproduce because they depend on specific temporal conditions.

Therefore, *we have isolated the application from the messaging implementation by using interfaces and
implementation classes.*

*Why?* 
Because testing a single application is much easier than testing multiple, distributed applications
connected by messaging channels.
single application allows us to trace through the complete
execution path, we do not need a complex start-up procedure to fire up all components.

##INSERT COOL EXAMPLES HERE 

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


#### If we had more time

1. Verify the Credit Bureau Operation

The Loan Broker accesses this service to obtain credit scores for
customers requesting a loan quote. 

In order to verify the correct operation of the external Credit Bureau service we could send periodic Test Messages("heartbeat") to the service.

Because the Credit Bureau service supports a Return Address it is easy to inject a Test Message without disturbing the existing message flow.

We simply provide a dedicated reply channel for test messages and avoid the need for a separate test message separator.

In order to verify the correct operation of the Credit Bureau service we need a Test Data Generator and a Test Data Verifier. The test data generator creates test data to be sent to the service under test. 

A Credit Bureau test message is very simple; the only field that is required is a social security
number (SSN). 

