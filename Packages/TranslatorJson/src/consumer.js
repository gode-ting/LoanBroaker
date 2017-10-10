const rabbitmq = require('../config/rabbitmq.js');
const producer = require('./producer.js');
const randomstring = require('randomstring');

module.exports.startConsumer = function (ampqConn) {
	let exchange = rabbitmq.consumer.exchange;
	let binding = rabbitmq.consumer.binding;

	ampqConn.createChannel((err, ch) => {
		let type = rabbitmq.consumer.type;

		ch.assertExchange(exchange, type, {
			durable: false
		});

		ch.assertQueue('', {
			exclusive: true
		}, (err, q) => {
			console.log('\nConsumer:\n [*] Waiting for messages in %s. To exit press CTRL+C', q.queue);
			ch.bindQueue(q.queue, exchange, binding);

			ch.consume(q.queue, (message) => {
				let messageId = randomstring.generate(6);
				console.log('\nConsumer:\n [x] %s', message.content.toString(), ' - id: ', messageId);
				producer.startProducer(ampqConn, message, messageId);
			}, {
				noAck: true
			});
		});

	});
};