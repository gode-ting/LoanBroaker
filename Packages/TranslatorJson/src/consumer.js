import rabbitmq from '../config/rabbitmq.js';
import producer from './producer.js';
import timestamp from './timestamp.js';

export default function (ampqConn) {
	let exchange = rabbitmq.consumer.exchange;
	let binding = rabbitmq.consumer.binding;

	ampqConn.createChannel((err, ch) => {
		let type = rabbitmq.consumer.type;

		ch.assertExchange(exchange, type, {
			durable: false
		});

		let newTimeStamp;

		ch.assertQueue('', {
			exclusive: true
		}, (err, q) => {
			newTimeStamp = timestamp.getTimeStamp();
			console.log(`\nConsumer ${newTimeStamp}\n [*] Waiting for messages in ${ q.queue}. To exit press CTRL+C`);
			ch.bindQueue(q.queue, exchange, binding);

			ch.consume(q.queue, (message) => {
				newTimeStamp = timestamp.getTimeStamp();
				console.log(`\nConsumer ${newTimeStamp}:\n [x] consumed message ${message.content.toString()}`);
				producer(ampqConn, message);
			}, {
				noAck: true
			});
		});

	});
}