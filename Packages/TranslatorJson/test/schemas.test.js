let expect = require('chai').expect;
let Ajv = require('ajv');
let ajv = Ajv({
	allErrors: true
});

let schemas = require('../config/schemas.json');
let bankSchema = schemas.$bankSchema;

describe('../config/schemas.json', () => {
	let validate;

	before(() => {
		validate = ajv.compile(bankSchema.properties);
	});

	describe('$bankSchema', () => {
		it('should be an object', () => {
			let expected = 'object';

			let actual = bankSchema;

			expect(actual).to.be.an(expected);
		});
	});

	describe('$bankSchema', () => {
		it('should validate schema against test json-objects', () => {
			// env.addSchema('bankSchema', bankSchema);
			let testSchemaOne = {
				ssn: "",
				creditScore: "",
				loanAmount: 100.1,
				loanDuration: "1992-28-04"
			}
			let testSchemaTwo = {
				creditScore: "",
				loanAmount: 100.1,
				loanDuration: "1992-28-04"
			}

			let actualOne = validate(testSchemaOne);
			let actualTwo = validate(testSchemaTwo)

			expect(actualOne).to.eql(true);
			expect(actualTwo).to.eql(false);
		});
	});

	// describe('$bankSchema', () => {
	// 	it('should return true when validated against schema and has correct structure', () => {

	// 	});
	// });
	// describe('$bankSchema', () => {
	// 	it('should return true when validated against schema and has correct structure', () => {

	// 	});
	// });
	// describe('$bankSchema', () => {
	// 	it('should return true when validated against schema and has correct structure', () => {

	// 	});
	// });
});