// dependencies
import express from 'express';
import cnf from 'cnf';

// Routes
import index from './index.js';
import ping from './ping.js';
import producer from './producer.js';
import consumer from './consumer.js';

export function main() {
	try {
		let app = express();

		app.get('/consumer', consumer);
		app.get('/produce', producer);
		app.get('/ping', ping);
		app.get('/', index);

		let port = cnf.http.port;
		app.listen(port, () => {
			console.log('Listening on port ', port);
		});
	} catch (error) {
		console.error(error);
	}
}