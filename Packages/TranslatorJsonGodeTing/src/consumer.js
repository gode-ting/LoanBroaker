import rabbitmq from '../config/rabbitmq.js';
// import translator from './translator.js';
import producer from './producer.js';
import getTimeStamp from './timestamp.js';

export default function (ampqConn) {
	let exchange = rabbitmq.consumer.exchange;
	let binding = rabbitmq.consumer.binding;

	ampqConn.createChannel((err, ch) => {
		let type = rabbitmq.consumer.type;

		ch.assertExchange(exchange, type, {
			durable: false
		});

		let timeStamp;

		ch.assertQueue('', {
			exclusive: true
		}, (err, q) => {
			timeStamp = getTimeStamp();
			console.log(`\nConsumer ${timeStamp}:\n [*] Waiting for messages in %s. To exit press CTRL+C`, q.queue);
			ch.bindQueue(q.queue, exchange, binding);

			ch.consume(q.queue, (message) => {
				timeStamp = getTimeStamp();
				console.log(`\nConsumer ${timeStamp}:\n [x] %s`, message.content.toString());
				producer(ampqConn, message);
			}, {
				noAck: true
			});
		});

	});
}