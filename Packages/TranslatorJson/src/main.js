// dependencies
import cnf from 'cnf';
import amqp from 'amqplib/callback_api';

// 
import producer from './producer.js';
import consumer from './consumer.js';
import rabbitmqConfig from '../config/rabbitmq.js';

export function main() {

	let host = rabbitmqConfig.connection.host;
	let username = rabbitmqConfig.connection.username;
	let password = rabbitmqConfig.connection.password;

	let amqpConn = null;

	amqp.connect(`amqp://${username}:${password}@${host}:`, (err, conn) => {
		if (err) {
			console.error('[AMPQ]', err.message);
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

		amqpConn = conn;
		whenConnected(amqpConn);
	});
}

export function whenConnected (amqpConn) {
	consumer(amqpConn);
}