export default {
	queues: {
		consumer: "LoanBroker9.banks_out",
		producer: "cphbusiness.bankJSON"
	},
	connection: {
		host: "datdb.cphbusiness.dk",
		port: "15672",
		username: "student",
		password: "cph"
	}
}