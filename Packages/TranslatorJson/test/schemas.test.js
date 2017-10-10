let expect = require('chai').expect;
let Ajv = require('ajv');
let ajv = Ajv({
	allErrors: true
});

let schemas = require('../config/schemas.json');
let bankSchema = schemas.$bankSchema;
let rules = require('./rules/bankSchemaProperties.js');

describe('../config/schemas.json', () => {
	let validateProperties;
	// let validateLoanAmount;

	before(() => {
		validateProperties = ajv.compile(bankSchema.properties);
		// validateLoanAmount = ajv.compile(bankSchema.properties.loanAmount);
	});

	describe('$bankSchema', () => {
		it('should be an object', () => {
			let expected = 'object';

			let actual = bankSchema;

			expect(actual).to.be.an(expected);
		});
	});

	rules.forEach((rule) => {
		describe.skip('$bankSchema.properties', () => {
			it('should validate properties-schema against test json-objects', () => {
				let expected = rule.output;

				let actual = validateProperties(rule.input);

				expect(actual).to.eql(expected);
			});
		});
	});
});