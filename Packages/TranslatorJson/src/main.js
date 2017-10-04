// dependencies
import express from 'express';
import http from 'http';
import cnf from 'cnf';

// Routes
import index from './index.js';

export function main() {
	try {
		let app = express();

		app.get('/', index);

		let server = http.createServer(app);

		let port = cnf.http.port;
		server.listen(port, () => {
			console.log('Listening on port ', port);
		});
	} catch (error) {
		console.error(error);
	}
}