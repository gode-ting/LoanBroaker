let connection = require('./connection');
let rabbitmq = require('../config/rabbitmq');

module.exports.main = function () {

	return new Promise((resolve, reject) => {
		console.log(' [x] consumer setting up connection');
		connection.getConnection()
			.then((conn) => {
				console.log(' [-] consumer connected');
				conn.createChannel((err, ch) => {
					if (err) {
						conn.close();
						console.error(`\nConsumer:\n[AMPQ] channel error - closing; ${err}`);
					}
					console.log('consumer running');

					let queue = rabbitmq.consumer.queue;

					ch.assertQueue(queue, { durable: false }, (err, queueOk) => {
						if (err) {
							console.error(err);
							reject(err);
						}
						ch.prefetch(1);

						ch.consume(queue, (message) => {
							console.log(` [x] received ${message} in consumer`);
							ch.close();
							conn.close();
							resolve(message);
						}, {noAck: true}, (err, ok) => {
							if (err) {
								console.error(err);
								reject(err);
							}
						});
					});
				});
			});

	});
};