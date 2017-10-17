export default function (jsonObject) {

	if (!jsonObject.ssn) {
		throw new Error('jsonObject.ssn not defined');
	}

	let ssn = parseInt(jsonObject.ssn);
	let creditScore = jsonObject.creditScore || '';
	let loanAmount = jsonObject.loanAmount || '';
	let loanDuration = jsonObject.loanDuration || '';

	let formattedJson = {
		ssn,
		creditScore,
		loanAmount,
		loanDuration
	};

	return formattedJson;
}