module.exports = {
	consumer: {
		type: 'direct',
		exchange: 'LoanBroker9.getRecipients_out',
		binding: 'bank-jyske-bank'
	},
	consumerGodeTing: {
		type: 'direct',
		exchange: 'LoanBroker9.getRecipients_out',
		binding: 'bank-nordea'
	},
	producer: {
		type: 'fanout',
		exchange: 'cphbusiness.bankJSON',
		replyTo: 'LoanBroker9.banks_out'
	},
	producerGodeTing: {
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