module.exports = {
	getFormattedJson
};

function getFormattedJson(jsonObject) {

	let ssn = jsonObject.ssn || '';
	let creditScore = jsonObject.creditScore || '';
	let loanAmount = jsonObject.loanAmount || '';
	let loanDuration = 365;

	let formattedJson = {
		ssn,
		creditScore,
		loanAmount,
		loanDuration
	};

	return formattedJson;
}