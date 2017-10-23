module.exports = {
	connection: {
		host: 'datdb.cphbusiness.dk',
		port: '15672',
		username: 'student',
		password: 'cph'
	},
	producer: {
		queue: 'LoanBroker9.getCreditScore_in'
	},
	consumer: {
		queue: 'LoanBroker9.aggregator_out'
	}
};