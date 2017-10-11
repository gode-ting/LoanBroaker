// dependencies
import amqp from 'amqplib/callback_api';

// Modules
import rabbitmqConfig from '../config/rabbitmq.js';
import consumer from './consumer.js';

// main function
export function main () {
	const host = rabbitmqConfig.connection.host;
	const username = rabbitmqConfig.connection.username;
	const password = rabbitmqConfig.connection.password;

	const connection = `amqp://${username}:${password}@${host}`;

	amqp.connect(connection, (err, conn) => {

		if (err) {
			console.error('[AMPQ] err:', err.message);

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

		console.log('[AMPQ] connected - TransonJsonGodeTing');

		consumer(conn);
	});
}