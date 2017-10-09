module.exports = getTestMessage();

function TestMessage(ssn, creditScore, loanAmount, date, loanDuration) {
	this.ssn = ssn;
	this.creditScore = creditScore;
	this.loanAmount = loanAmount;
	this.date = date;
	this.loanDuration = loanDuration;
}

function getTestMessage() {
	let ssn = "010192-9999";
	let creditScore = "500";
	let loanAmount = "1000.0";
	let date = new Date();
	let loanDuration = date;

	let testMessage = new TestMessage(ssn, creditScore, loanAmount, date, loanAmount);

	let message = JSON.stringify({
		ssn: ssn,
		creditScore,
		creditScore,
		loanAmount: loanAmount,
		loanDuration: loanDuration
	});
	return message;
}