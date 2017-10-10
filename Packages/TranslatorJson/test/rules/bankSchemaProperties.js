module.exports = [
	{
		input: {
			ssn: '1992-28-04',
			creditScore: '850',
			loanAmount: 100.1,
			loanDuration: '350'
		},
		output: true
	},
	{
		input: {
			ssn: '2000-12-12',
			creditScore: '100',
			loanAmount: 200,
			loanDuration: ''
		},
		output: true
	},
	{
		input: {
			ssn: '2010-30-11',
			creditScore: '500',
			loanAmount: '100',
			loanDuration: '100'
		},
		output: false
	},
	{
		input: {
			ssn: 2010 - 30 - 11,
			creditScore: '100',
			loanAmount: 100,
			loanDuration: '500'
		},
		output: false
	},
	{
		input: {
			ssn: '2013-19-12',
			creditScore: 100,
			loanAmount: 200,
			loanDuration: '250'
		},
		output: false
	}
];