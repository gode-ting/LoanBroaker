export default {
	queues: {
		// consumer: "LoanBroker9.banks_out",
		producer: "cphbusiness.bankJSON",
		replyTo: ""
	},
	producer: {
		exchange: 'cphbusiness.bankJSON',
		replyTo: 'LoanBroker9.banks_out'
	},
	consumer: {
		exchange: "LoanBroker9.getRecipients_out",
		binding: "bank-jyske-bank"
	},
	connection: {
		host: "datdb.cphbusiness.dk",
		port: "15672",
		username: "student",
		password: "cph"
	}
}