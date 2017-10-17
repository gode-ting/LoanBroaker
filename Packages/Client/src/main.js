let express = require('express');
// let path = require('path');
let logger = require('morgan');
let cnf = require('cnf');
let bodyParser = require('body-parser');

let index = require('./index.js');

export function main() {
	let app = express();

	app.use(logger('dev'));
	app.use(bodyParser.json({type: 'application/json'}));
	// app.use(bodyParser.urlencoded({ extended: false }));

	app.use('/', index);

	// catch 404 and forward to error handler
	app.use(function (req, res, next) {
		let err = new Error('Not Found');
		err.status = 404;
		next(err);
	});

	// error handler
	app.use(function (err, req, res, next) {
		// set locals, only providing error in development
		res.locals.message = err.message;
		res.locals.error = req.app.get('env') === 'development' ? err : {};

		// render the error page
		res.status(err.status || 500);
		res.json({error: err.message});
	});

	let port = cnf.http.port;
	app.listen(port, () => {
		console.log(`Listening on port ${port}`);
	});

}
