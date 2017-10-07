# TranslatorJson

**Table of contents:**

* [Commands](#commands)
* [Communicating with the Translator](#communicating-with-the-translator)

Made with node.js

## Commands

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

#### TestMessage.js

Just a constructor for testing purposes.

```javascript
// Example usage:
import testMessage from './TestMessage.js';
console.log(testMessage);
```

## Communicating with the Translator

