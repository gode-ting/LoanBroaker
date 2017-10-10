module.exports = {
	producer: {
		type: 'fanout',
		exchange: 'cphbusiness.bankJSON',
		replyTo: 'LoanBroker9.banks_out'
	},
	consumer: {
		type: 'direct',
		exchange: 'LoanBroker9.getRecipients_out',
		binding: 'bank-jyske-bank'
	},
	producerGodeTing: {
		type: 'fanout',
		exchange: 'LoanBroker9.TingGodRabbitMQBank',
		replyTo: 'LoanBroker9.banks_out'
	},
	consumerGodeTing: {
		type: 'direct',
		exchange: 'LoanBroker9.getRecipients_out',
		binding: 'TingGodRabbitMQBank'
	},
	connection: {
		host: 'datdb.cphbusiness.dk',
		port: '15672',
		username: 'student',
		password: 'cph'
	}
};