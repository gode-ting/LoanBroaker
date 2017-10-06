import rabbitmq from '../config/rabbitmq.js';
import producer from './producer.js';

export default function (ampqConn) {
	let consumeQueue = rabbitmq.queues.consumer;

	ampqConn.createChannel((err, ch) => {
		ch.assertQueue(consumeQueue, {durable: false});
		console.log('Waiting for messages in consumer');

		ch.consume(consumeQueue, (message) => {
			console.log('Received: ', message.content.toString());
			producer(ampqConn, message);
		}, {noAck: true});
	});
}