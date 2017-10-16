let connection = require('./connection');
let rabbitmq = require('../config/rabbitmq');

module.exports.main = function () {
	connection.getConnection()
		.then((conn) => {
			conn.createChannel((err, ch) => {
				if (err) {
					conn.close();
					console.error(`\nConsumer:\n[AMPQ] channel error - closing; ${err}`);
				}
			});
		});
};