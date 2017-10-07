let expect = require('chai').expect;

let javaInit = require('../src/javaInit.js');
let java = javaInit.getJavaInstance();

let javaTranslator = require('../src/javaTranslator.js');

describe('../src/javaTranslator.js', () => {
	describe('transformHashMapToJson', () => {
		it('should return a java HashMap converted to json', () => {
			let javaHashmap = java.newInstanceSync('java.util.HashMap');
			javaHashmap.putSync("ssn", 'This is a ssn');
			javaHashmap.putSync("creditScore", 'credit score!');
			javaHashmap.putSync("loanAmount", 'loan amount!');
			javaHashmap.putSync("loanDuration", 'loan duration!');

			let expected = {
				ssn: 'This is a ssn',
				creditScore: 'credit score!',
				loanAmount: 'loan amount!',
				loanDuration: 'loan duration!'
			};

			let actual = javaTranslator.transformHashMapToJson(javaHashmap);

			expect(actual).to.be.an('object');
			expect(actual).to.eql(expected);
		});
	});
});