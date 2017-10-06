import rabbitmq from '../config/rabbitmq.js';
// import offlineQueue from './offlineQueue.js';

export default function (ampqConn, message) {
	let producerQueue = rabbitmq.queues.producer;
	let replyTo = rabbitmq.queues.consumer;

	ampqConn.createChannel((err, ch) => {
		if (err) {
			ampqConn.close();
			console.error('[AMPQ] connection error - closing; ', err);
		}
		ch.assertQueue(producerQueue);
		ch.publish('', producerQueue, Buffer.from(message), {
			replyTo: replyTo
		});
	});
	setTimeout(() => {
		ampqConn.close();
		console.log('Conn closed');
	}, 500);
}