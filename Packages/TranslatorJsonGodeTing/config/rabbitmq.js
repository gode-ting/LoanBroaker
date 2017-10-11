module.exports = {
	consumer: {
		type: 'direct',
		exchange: 'LoanBroker9.getRecipients_out',
		binding: 'bank-nordea'
	},
	producer: {
		type: 'fanout',
		exchange: 'LoanBroker9.TingGodRabbitMQBank',
		replyTo: 'LoanBroker9.banks_out'
	},
	connection: {
		host: 'datdb.cphbusiness.dk',
		port: '15672',
		username: 'student',
		password: 'cph'
	}
};