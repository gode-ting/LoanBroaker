let amqp = require('amqplib/callback_api');
let rabbitmqConfig = require('../config/rabbitmq');

module.exports = {
	getConnection
};

function getConnection() {
	const host = rabbitmqConfig.connection.host;
	const username = rabbitmqConfig.connection.username;
	const password = rabbitmqConfig.connection.password;

	const connection = `amqp://${username}:${password}@${host}`;

	return new Promise((resolve, reject) => {
		amqp.connect(connection, (err, conn) => {

			if (err) {
				console.error('[AMPQ] err:', err.message);

				// Restart main if error on connection
				return setTimeout(getConnection(), 1000);
			}
			conn.on('error', (err) => {
				if (err.message !== 'Conection closing') {
					console.error('[AMPQ] conn err: ', err.message);
				}
			});
			conn.on('close', () => {
				console.info('[AMPQ] connection closed');
			});

			resolve(conn);
		});
	});
}