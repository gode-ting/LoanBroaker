module.exports = {
	consumer: {
		type: 'direct',
		exchange: 'LoanBroker9.getRecipients_out',
		binding: 'bank-jyske-bank'
	},
	producer: {
		exchangeType: 'fanout',
		exchange: 'cphbusiness.bankJSON',
		replyTo: 'LoanBroker9.banks_out',
		bankID: 'bankJSON',
		type: 'json'
	},
	connection: {
		host: 'datdb.cphbusiness.dk',
		port: '15672',
		username: 'student',
		password: 'cph'
	}
};