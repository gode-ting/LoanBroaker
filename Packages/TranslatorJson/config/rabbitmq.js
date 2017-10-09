export default {
	producer: {
		type: 'fanout',
		// exchange: 'cphbusiness.bankJSON',
		exchange: 'cphbusiness.bankJSON'/*'LoanBroker9.TingGodRabbitMQBank'*/,
		replyTo: 'LoanBroker9.banks_out'
	},
	consumer: {
		type: 'direct',
		exchange: 'LoanBroker9.getRecipients_out',
		binding: 'bank-jyske-bank'
	},
	connection: {
		host: 'datdb.cphbusiness.dk',
		port: '15672',
		username: 'student',
		password: 'cph'
	}
};