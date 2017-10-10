const rabbitmq = require('../config/rabbitmq.js');
// const producer = require('./producer.js');

module.exports.startConsumer = function (ampqConn) {
	let exchange = rabbitmq.consumerGodeTing.exchange;
	let binding = rabbitmq.consumerGodeTing.binding;

	ampqConn.createChannel((err, ch) => {
		let type = rabbitmq.consumerGodeTing.type;

		ch.assertExchange(exchange, type, {
			durable: false
		});

		ch.assertQueue('', {
			exclusive: true
		}, (err, q) => {
			console.log('\nConsumerGodeTing:\n [*] Waiting for messages in %s . To exit press CTRL+C', q.queue);
			ch.bindQueue(q.queue, exchange, binding);

			ch.consume(q.queue, (message) => {
				console.log('\nConsumerGodeTing:\n [x] %s', message.content.toString());
				// producer(ampqConn, message);
			}, {
				noAck: true
			});
		});

	});
};