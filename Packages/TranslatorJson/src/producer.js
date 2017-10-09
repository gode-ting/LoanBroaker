import rabbitmq from '../config/rabbitmq.js';
import offlineQueue from './offlineQueue.js';
import javaTranslator from './javaTranslator.js';

export default function (ampqConn, message) {
	let producerQueue = rabbitmq.queues.producer;
	let replyTo = rabbitmq.producer.replyTo;

	ampqConn.createChannel((err, ch) => {
		if (err) {
			ampqConn.close();
			console.error('[AMPQ] connection error - closing; ', err);
		}

		let type = 'fanout';
		let exchange = rabbitmq.producer.exchange;
		let headers = {type: 'json'};

		ch.assertExchange(exchange, type, {
			durable: false
		});

		ch.publish(exchange, '', Buffer.from(message.content.toString()), {
			headers: headers,
			replyTo: replyTo
		});
	});
	// setTimeout(() => {
	// 	ampqConn.close();
	// 	console.log('Conn closed');
	// }, 500);
}