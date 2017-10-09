import rabbitmq from '../config/rabbitmq.js';
import producer from './producer.js';

export default function (ampqConn) {
	let exchange = rabbitmq.consumer.exchange;
	let binding = rabbitmq.consumer.binding;

	ampqConn.createChannel((err, ch) => {
		let type = rabbitmq.consumer.type;

		ch.assertExchange(exchange, type, {
			durable: false
		});

		ch.assertQueue('', {
			exclusive: true
		}, (err, q) => {
			console.log(' [*] Waiting for messages in %s. To exit press CTRL+C', q.queue);
			ch.bindQueue(q.queue, exchange, binding);

			ch.consume(q.queue, (message) => {
				console.log('Message: ', message);
				console.log(' [x] %s', message.content.toString());
				// offlineQueue.pushToOfflineQueue(message);
				producer(ampqConn, message);
			}, {
				noAck: true
			});
		});

	});
}