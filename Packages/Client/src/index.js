let express = require('express');
let router = express.Router();
let producer = require('./producer');

/* GET home page. */
router.get('/', function (req, res, next) {
	let endpoints =
		[
			{ endpoint: '/loanRequest', method: 'POST', type: 'json', required: {
				ssn: 'string',
				loanAmount: 'number',
				loanDuration: 'number'
			}},
			{ endpoint: '/loanResponse', method: 'GET', response: 'json' }
		];
	res.json(endpoints);
});

router.post('/loanRequest', (req, res, next) => {
	let ssn = req.body.ssn;
	let loanAmount = req.body.loanAmount;
	let loanDuration = req.body.loanDuration;

	let request = {
		ssn,
		loanAmount,
		loanDuration
	};

	producer.main(request)
		.then(() => {
			let response = {
				status: 'success',
				received: {
					ssn,
					loanAmount,
					loanDuration
				}
			};
			return res.json({ response });
		}, (err) => {
			res.json({ err: err });
		});
});

router.get('/loanResponse', (req, res, next) => {
	res.json({ status: 'success' });
});

module.exports = router;
