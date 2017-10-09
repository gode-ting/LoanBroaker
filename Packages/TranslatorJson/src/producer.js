import rabbitmq from '../config/rabbitmq.js';

export default function (ampqConn, message) {
	let replyTo = rabbitmq.producer.replyTo;

	ampqConn.createChannel((err, ch) => {
		if (err) {
			ampqConn.close();
			console.error('[AMPQ] connection error - closing; ', err);
		}

		let type = rabbitmq.producer.type;
		let exchange = rabbitmq.producer.exchange;
		let headers = {type: 'json'};

		ch.assertExchange(exchange, type, {
			durable: false
		});

		console.log('Exchange: ', exchange);

		ch.publish(exchange, '', Buffer.from(message.content.toString()), {
			headers: headers,
			replyTo: replyTo
		});
		console.log('Successfully sent message');
	});
	// setTimeout(() => {
	// 	ampqConn.close();
	// 	console.log('Conn closed');
	// }, 500);
}

function translateJson () {

}