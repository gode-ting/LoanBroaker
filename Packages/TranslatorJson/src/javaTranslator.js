module.exports = {
	transformHashMapToJson
};

let javaInit = require('./javaInit.js');
let java = javaInit.getJavaInstance();

function transformHashMapToJson(hashMap) {
	let ssn = hashMap.getSync('ssn');
	let creditScore = hashMap.getSync('creditScore');
	let loanAmount = hashMap.getSync('loanAmount');
	let loanDuration = hashMap.getSync('loanDuration');

	let jsonObject = {
		ssn,
		creditScore,
		loanAmount,
		loanDuration
	}

	return jsonObject;
};