export default function (jsonObject) {

	let ssn = jsonObject.ssn || '';
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