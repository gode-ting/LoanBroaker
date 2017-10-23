let expect = require('chai').expect;

let timestamp = require('../src/timestamp.js');

describe('../src/timestamp.js', () => {
	describe('getTimestamp()', () => {
		it('should return a time stamp', () => {

			// time stamp format: 00:00:00
			let expected = true;
			let actual = timestampRegex.test(timestamp.getTimeStamp());

			expect(actual).to.eql(expected);
		});
	});

	describe('timestampRegex', () => {
		it('should match a time stamp with a valid format', () => {
			let timestamp = '00:00:00';
			let timestamp2 = '1:1:1';
			let timestamp3 = '24:10:5';

			let expected = true;

			let actual = timestampRegex.test(timestamp);
			let actual2 = timestampRegex.test(timestamp2);
			let actual3 = timestampRegex.test(timestamp3);

			expect(actual).to.eql(expected);
			expect(actual2).to.eql(expected);
			expect(actual3).to.eql(expected);
		});
	});

	let timestampRegex = /(\d{2}|\d{1}):(\d{2}|\d{1}):(\d{2}|\d{1})/;
});