import amqp from 'amqp';
import rabbitmq from '../config/rabbitmq.js';

export default function (req, res) {
	let host = rabbitmq.connection.host;
	let username = rabbitmq.connection.username;
	let password = rabbitmq.connection.password;

	let connection = amqp.createConnection({
		host: host,
		login: username,
		password: password
	});

	connection.on('error', (e) => {
		console.error('error from ampq: ', e);
	});

	connection.on('ready', () => {
		let queue = rabbitmq.queues.consumer;
		let ssn = "010192-9999";
		let creditScore = "500";
		let loanAmount = "1000.0";
		let date = new Date();
		let loanDuration = date;

		let message = { ssn: ssn, creditScore, creditScore, loanAmount: loanAmount, loanDuration: loanDuration };
		connection.publish(rabbitmq.queues.producer, message, {}, (err, msg) => {
			if (err) {
				console.error(err);
				res.json({error: err});
			}
			res.json({success: msg});
		})
	});
}