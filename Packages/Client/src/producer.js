let connection = require('./connection');
let rabbitmq = require('../config/rabbitmq');

module.exports = {
	main
};

function main(request) {
	let messageSent = false;
	return new Promise((resolve, reject) => {
		connection.getConnection()
			.then((conn) => {
				conn.createChannel((err, ch) => {
					ch.on('close', () => {
						console.info('[AMQP] channel closed');
					});
					if (err) {
						conn.close();
						console.error(`\nproducer:\n[AMPQ] connection error (producer) - closing; ${err}`);
						reject(err);
					}

					let queue = rabbitmq.producer.queue;
					let opts = {};

					messageSent = ch.sendToQueue(queue, Buffer.from(JSON.stringify(request)), opts);
					setTimeout(() => {
						if (messageSent) {
							resolve(true);
							ch.close();
							conn.close();
						}
					}, 500);
				});
			});
	});
}
