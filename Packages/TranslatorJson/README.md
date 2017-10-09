# TranslatorJson

**Table of contents:**

* [Development](#development)
* [Communicating with the Translator](#communicating-with-the-translator)

Made with node.js

## Development

### Commands

`npm install` or `npm i` install all necescary dependencies.

`npm run dev` start in development mode - nodemon will run and watch for changes (restart when it notices any).

`npm run start` start in production mode.

`npm run test` run all tests.

## Structure

### config

Holds all configs.

#### rabbitmg.js

All rabbitmq configurations.

### src

Holds all server parts.

#### consumer.js

Responsible for consuming message sent. Will pass those to the producer.

#### main.js

Responsible for starting and running the server - will connect to the database, and start the consumer.

#### offlineQueue.js

First intended as a "secure" queue if a message was not sent properly, but apparently the publish function does not support reporting this, so currently it's unused.

#### producer.js

Responsible for producing/publishing messages. Will publish message received by the consumer to the database.

### Test

Holds all tests. Tests can be run with the `npm run test` command, which will run all tests in this folder that is named as `*.test.js`.

#### TestMessage.js

Just a constructor for testing purposes.

```javascript
// Example usage:
import testMessage from '../test/TestMessage.js';
console.log(testMessage);
```

## Communicating with the Translator

### Consumer

The translator listens to the **exchange** `LoanBroker9.getRecipients_out` and the **binding** `bank-jyske-bank`.

```javascript
consumer: {
    type: "direct",
    exchange: "LoanBroker9.getRecipients_out",
    binding: "bank-jyske-bank"
}
```

### Producer

The consumer passes any received messages to the consumer, which sends it's messages to **exchange** `cphbusiness.bankJSON` with a replyTo header `LoanBroker9.banks_out`.

```javascript
producer: {
    type: 'fanout',
    exchange: 'cphbusiness.bankJSON',
    replyTo: 'LoanBroker9.banks_out'
},
```