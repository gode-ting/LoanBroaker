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
		message.creditScore = undefined
		message.rki = false

		var newMessage = {'ssn':1605789787, 'loanAmount': 10.0, 'loanDuration': 360, 'rki':false};

		console.log('hej fra emil', message.toString())
		console.log('hej igen du', new Buffer(message.toString()))
		ch.publish(exchange, '', Buffer.from(newMessage.toString()), {
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