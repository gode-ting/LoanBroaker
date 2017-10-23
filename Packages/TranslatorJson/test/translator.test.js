let expect = require('chai').expect;

let translator = require('../src/translator.js');

describe('../src/transaltor.js', () => {

	describe('getFormattedJson()', () => {
		it('should return a formatted json object, and sort off no needed properties', () => {
			let testJson = {
				loanDuration: '365',
				creditScore: '750',
				loanAmount: '1000.01',
				ssn: '280492',
				invalid1: 'random1',
				invalid2: 'random2'
			};

			let expectedJson = {
				ssn: 280492,
				creditScore: '750',
				loanAmount: '1000.01',
				loanDuration: '365'
			};

			let actualJson = translator.getFormattedJson(testJson);

			expect(actualJson).to.eql(expectedJson);
		});
		it('should return a formatted json object, and handle undefined values', () => {
			let testJson = {
				creditScore: '750',
				loanAmount: '1000.01',
				loanDuration: '365'
			};

			let expectedJson = {
				ssn: '',
				creditScore: '750',
				loanAmount: '1000.01',
				loanDuration: '365'
			};

			let actualJson = translator.getFormattedJson(testJson);

			expect(actualJson).to.eql(expectedJson);
		});

		it('should handle an object with no defined values', () => {
			let testJson = {
			};

			let expectedJson = {
				ssn: '',
				creditScore: '',
				loanAmount: '',
				loanDuration: 365
			};

			let actualJson = translator.getFormattedJson(testJson);

			expect(actualJson).to.eql(expectedJson);
		});
	});
});