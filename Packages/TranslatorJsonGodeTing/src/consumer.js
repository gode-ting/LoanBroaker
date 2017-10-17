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
			console.log(`\nConsumer ${timeStamp}\n [*] Waiting for messages in ${q.queue}. To exit press CTRL+C`);
			ch.bindQueue(q.queue, exchange, binding);

			ch.consume(q.queue, (message) => {
				timeStamp = getTimeStamp();
				console.log(`\nConsumer ${timeStamp}\n [x] consumed message ${message.content.toString()}`);
				producer(ampqConn, message);
			}, {
				noAck: true
			});
		});

	});
}