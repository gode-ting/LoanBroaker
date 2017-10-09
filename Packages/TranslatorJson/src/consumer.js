import rabbitmq from '../config/rabbitmq.js';
import producer from './producer.js';
import testMessage from './TestMessage.js';
import offlineQueue from './offlineQueue.js';

export default function (ampqConn) {
	let exchange = rabbitmq.consumer.exchange;
	let binding = rabbitmq.consumer.binding;

	console.log('CONSUMER');
	ampqConn.createChannel((err, ch) => {
		let type = 'direct';

		ch.assertExchange(exchange, type, {durable: false});
	
		ch.assertQueue('', {exclusive: true}, (err, q) => {
			console.log(" [*] Waiting for messages in %s. To exit press CTRL+C", q.queue);
			ch.bindQueue(q.queue, exchange, binding);
			
			ch.consume(q.queue, (message) => {
				console.log(" [x] %s", message.content.toString());
				// offlineQueue.pushToOfflineQueue(message);
				producer(ampqConn, message);
			}, {noAck: true});
		});

	});
}

// ch.bind(queue, source, pattern, args) skal gøres når serveren starter. 
// queue = assertQueue
// source = exchange, som defineres
// pattern = bindingen, som defineres
// args = ligegyldigt (måske)