let rabbitmq = require('../config/rabbitmq.js');
let translator = require('./translator');

module.exports.startProducer = function (ampqConn, message, messageId) {

	let replyTo = rabbitmq.producer.replyTo;

	ampqConn.createChannel((err, ch) => {
		if (err) {
			ampqConn.close();
			console.error('\nproducer\n[AMPQ] connection error (producer) - closing; ', err);
		}

		let type = rabbitmq.producer.type;
		let exchange = rabbitmq.producer.exchange;
		let headers = {
			type: 'json'
		};

		ch.assertExchange(exchange, type, {
			durable: false
		});

		//	Example of a message correctly formatted and ready to be send to cphbusiness.bankjson
		//var newMessage = {'ssn':1605789787, 'creditScore':749, 'loanAmount': 10.0, 'loanDuration': 360};
		let jsonObject = JSON.parse(message.content.toString());
		let formattedObject = translator.getFormattedJson(jsonObject);

		ch.publish(exchange, '', Buffer.from(JSON.stringify(formattedObject)), {
			headers: headers,
			replyTo: replyTo
		});
		console.log(` [+] Successfully sent message ${messageId} from producer`);
	});
};