// import amqp from 'amqp';
import amqp from 'amqplib/callback_api';
import rabbitmq from '../config/rabbitmq.js';

export default function (ampqConn) {
	let host = rabbitmq.connection.host;
	let username = rabbitmq.connection.username;
	let password = rabbitmq.connection.password;
	let producerQueue = rabbitmq.queues.producer;
	let replyTo = rabbitmq.queues.consumer;

	let ssn = "010192-9999";
	let creditScore = "500";
	let loanAmount = "1000.0";
	let date = new Date();
	let loanDuration = date;

	let message = JSON.stringify({ ssn: ssn, creditScore, creditScore, loanAmount: loanAmount, loanDuration: loanDuration });

	amqp.connect(`amqp://${username}:${password}@${host}`, (err, conn) => {
		if (err) {
			console.error(err);
		}
		conn.on('error', (err) => {
			return res.json({error: err});
		});
		conn.createChannel((err, ch) => {
			if (err) {
				console.error(err);
			}
			ch.assertQueue(producerQueue);
			ch.publish('', producerQueue, Buffer.from(message), {replyTo: replyTo});
		});
		setTimeout(() => {
			conn.close();
			return res.json({success: true});
		}, 500);
	});

	// let connection = amqp.createConnection({
	// 	host: host,
	// 	login: username,
	// 	password: password
	// });

	// connection.on('error', (e) => {
	// 	console.error('error from ampq: ', e);
	// });

	// connection.on('ready', () => {
	// 	let queue = rabbitmq.queues.consumer;
	// 	let ssn = "010192-9999";
	// 	let creditScore = "500";
	// 	let loanAmount = "1000.0";
	// 	let date = new Date();
	// 	let loanDuration = date;

	// 	let message = { ssn: ssn, creditScore, creditScore, loanAmount: loanAmount, loanDuration: loanDuration };
	// 	connection.publish(rabbitmq.queues.producer, message, {}, (err, msg) => {
	// 		if (err) {
	// 			console.error(err);
	// 			return res.json({error: err});
	// 		}
	// 		return res.json({success: msg});
	// 	})
	// });
}