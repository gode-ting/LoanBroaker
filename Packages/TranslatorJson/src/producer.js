import rabbitmq from '../config/rabbitmq.js';
import translator from './translator.js';
import getTimeStamp from './timestamp.js';

export default function (ampqConn, message) {
	let replyTo = rabbitmq.producer.replyTo;

	ampqConn.createChannel((err, ch) => {
		let timeStamp;

		timeStamp = getTimeStamp();
		if (err) {
			ampqConn.close();
			console.error(`\nproducer ${timeStamp}:\n[AMPQ] connection error (producer) - closing; `, err);
		}

		let type = rabbitmq.producer.type;
		let exhangeType = rabbitmq.producer.exchangeType;
		let bankID = rabbitmq.producer.bankID;
		let exchange = rabbitmq.producer.exchange;
		let headers = {
			type,
			bankID
		};

		ch.assertExchange(exchange, exhangeType, {
			durable: false
		});

		let jsonObject = JSON.parse(message.content.toString());
		let formattedObject = translator(jsonObject);

		ch.publish(exchange, '', Buffer.from(JSON.stringify(formattedObject)), {
			headers: headers,
			replyTo: replyTo
		});
		console.log(`\nproducer ${timeStamp}:\n [+] Successfully sent message`);
	});
}