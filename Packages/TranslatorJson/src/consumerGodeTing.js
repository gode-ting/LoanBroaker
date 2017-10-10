const rabbitmq = require('../config/rabbitmq.js');
const producer = require('./producerGodeTing');
const randomstring = require('randomstring');

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
				let messageId = randomstring.generate(6);
				console.log('\nConsumerGodeTing:\n [x] %s', message.content.toString(), ' - id: ', messageId);
				producer.startProducer(ampqConn, message, messageId);
			}, {
				noAck: true
			});
		});

	});
};