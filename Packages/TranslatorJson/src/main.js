// dependencies
import amqp from 'amqplib/callback_api';

// modules
import consumer from './consumer.js';
import rabbitmqConfig from '../config/rabbitmq.js';

// main function
export function main() {

	let host = rabbitmqConfig.connection.host;
	let username = rabbitmqConfig.connection.username;
	let password = rabbitmqConfig.connection.password;

	let connection = `amqp://${username}:${password}@${host}`;

	console.log('Connecting to: ', connection);

	amqp.connect(connection, (err, conn) => {
		if (err) {
			console.error('[AMPQ]', err.message);

			// Restart main if error on connection
			return setTimeout(main(), 1000);
		}
		conn.on('error', (err) => {
			if (err.message !== 'Conection closing') {
				console.error('[AMPQ] conn err: ', err.message);
			}
		});
		conn.on('close', () => {
			console.error('[AMPQ] reconnecting');
			return setTimeout(main(), 1000);
		});

		console.log('AMPQ connected');

		consumer(conn);
	});
}