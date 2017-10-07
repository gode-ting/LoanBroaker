let expect = require('chai').expect;

let offlineQueue = require('../src/offlineQueue.js');

describe('../src/offlineQueue.js', () => {
	describe('QueueElement()', () => {
		it('should create a new QueueElement', () => {
			let expectedString = 'string';

			let actualString = new offlineQueue.QueueElement(expectedString);
			expect(actualString.content).to.eql(expectedString);
			expect(actualString).to.be.an('object');
		});
	});

	describe('pushToOfflineQueue()', () => {
		it('should push elemnts to the queue', () => {
			let expected = true;

			let actualPushOne = offlineQueue.pushToOfflineQueue('test');
			let actualPushTwo = offlineQueue.pushToOfflineQueue('test');

			expect(actualPushOne).to.eql(expected);
			expect(actualPushTwo).to.eql(expected);
		});
	});

	describe('getOfflineQueue()', () => {
		it('should return the offline queue', () => {
			let expected = [
				'test',
				'test'
			]

			let actual = offlineQueue.getOfflineQueue();

			expect(actual).to.eql(expected);
		});
	});

	describe('shiftOfflineQueue()', () => {
		it('should shift() element from the queue', () => {
			let expected = 'test';

			let actualOne = offlineQueue.shiftOfflineQueue();
			let actualTwo = offlineQueue.shiftOfflineQueue();

			expect(actualOne).to.eql(expected);
			expect(actualTwo).to.eql(expected);
		});
	});
});