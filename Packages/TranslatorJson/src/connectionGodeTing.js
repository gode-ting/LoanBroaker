const amqp = require('amqplib/callback_api');
const rabbitmqConfig = require('../config/rabbitmq.js');

const consumer = require('./consumerGodeTing.js');

(function () {
	let host = rabbitmqConfig.connection.host;
	let username = rabbitmqConfig.connection.username;
	let password = rabbitmqConfig.connection.password;

	let connection = `amqp://${username}:${password}@${host}`;

	amqp.connect(connection, (err, conn) => {
		if (err) {
			console.error('[AMPQ]', err.message);

			// Restart main if error on connection
			return setTimeout(connection(), 1000);
		}
		conn.on('error', (err) => {
			if (err.message !== 'Conection closing') {
				console.error('[AMPQ] conn err: ', err.message);
			}
		});
		conn.on('close', () => {
			console.error('[AMPQ] reconnecting');
			return setTimeout(connection(), 1000);
		});

		console.log('AMPQ connected - connectionGodeTing');

		consumer.startConsumer(conn);
	});
})();