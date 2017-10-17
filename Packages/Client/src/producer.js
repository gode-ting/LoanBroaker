let connection = require('./connection');
let rabbitmq = require('../config/rabbitmq');

module.exports = {
	main
};

function main(request) {
	let messageSent = false;
	let stringifyedRequest = JSON.stringify(request);

	return new Promise((resolve, reject) => {
		console.log(' [x] producer setting up connection');
		connection.getConnection()
			.then((conn) => {
				console.log(' [-] producer connected');
				conn.createChannel((err, ch) => {
					ch.on('close', () => {
						console.info('[AMQP] channel closed');
					});
					if (err) {
						conn.close();
						console.error(`\nproducer:\n[AMPQ] channel error (producer) - closing; ${err}`);
						reject(err);
					}

					let queue = rabbitmq.producer.queue;
					let opts = {};

					ch.assertQueue(queue, {
						passive: false,
						durable: false,
						exclusive: false,
						autoDelete: null
					}, (err, q) => {
						if (err) {
							console.error(err);
						}
					});

					console.log(` [-] sending message to ${queue}`);
					messageSent = ch.sendToQueue(queue, Buffer.from(stringifyedRequest), opts);

					setTimeout(() => {
						if (messageSent) {
							console.log(` [+] successfully sent request ${stringifyedRequest} from producer.\nClosing channel and connection`);
							ch.close();
							conn.close();
							resolve(true);
						}
					}, 500);
				});
			});
	});
}
