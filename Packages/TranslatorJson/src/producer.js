import rabbitmq from '../config/rabbitmq.js';
import translator from './translator.js';
import timestamp from './timestamp.js';

export default function (ampqConn, message) {
	let replyTo = rabbitmq.producer.replyTo;

	ampqConn.createChannel((err, ch) => {
		let newtimeStamp;

		newtimeStamp = timestamp.getTimeStamp();
		if (err) {
			ampqConn.close();
			console.error(`\nproducer ${newtimeStamp}:\n[AMPQ] connection error (producer) - closing; ${err}`);
		}

		let type = rabbitmq.producer.type;
		let exchangeType = rabbitmq.producer.exchangeType;
		let bankID = rabbitmq.producer.bankID;
		let exchange = rabbitmq.producer.exchange;
		let headers = {
			type,
			bankID
		};

		ch.assertExchange(exchange, exchangeType, {
			durable: false
		});

		let jsonObject = JSON.parse(message.content.toString());
		let formattedObject = translator.getFormattedJson(jsonObject);

		ch.publish(exchange, '', Buffer.from(JSON.stringify(formattedObject)), {
			headers: headers,
			replyTo: replyTo
		});
		console.log(`\nproducer ${newtimeStamp}:\n [+] Successfully sent message`);
	});
}