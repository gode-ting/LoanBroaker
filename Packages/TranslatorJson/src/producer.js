import rabbitmq from '../config/rabbitmq.js';
import offlineQueue from './offlineQueue.js';

export default function (ampqConn) {
	let producerQueue = rabbitmq.queues.producer;
	let replyTo = rabbitmq.queues.consumer;

	ampqConn.createChannel((err, ch) => {
		if (err) {
			ampqConn.close();
			console.error('[AMPQ] connection error - closing; ', err);
		}
		ch.assertQueue(producerQueue);
		// let queue = offlineQueue.getOfflineQueue();
		while (true) {
			let nextElement = offlineQueue.shiftOfflineQueue();
			if (!nextElement) return;
			console.log('Next element (', typeof nextElement,  nextElement);
			ch.publish('', producerQueue, Buffer.from(nextElement.content), {
				replyTo: replyTo
			});
		}
	});
	// setTimeout(() => {
	// 	ampqConn.close();
	// 	console.log('Conn closed');
	// }, 500);
}