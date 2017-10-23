let expect = require('chai').expect;

let messageMap = require('../src/messageMap');

describe('../src/messageMap', () => {

	let testmap;

	beforeEach('should set testmap to a new map', () => {
		testmap = new Map();
	});

	describe('createMap()', () => {
		it('should create a new map and return true', () => {
			let expected = true;

			let actual = messageMap.createMap();

			expect(actual).to.eql(expected);
		});
	});

	describe('addToMap()', () => {
		it('should add key and value pair to map if dont exist, else return false', () => {
			let expectedInsert = true;
			let expectedNotInsert = false;

			let key = 'foo';
			let value = 'bar';

			let actualInsert = messageMap.addToMap(key, value);
			let actualNotInsert = messageMap.addToMap(key, value);

			expect(actualInsert).to.eql(expectedInsert);
			expect(actualNotInsert).to.eql(expectedNotInsert);
		});
	});

	describe('getKey()', () => {
		it('should return value for the key', () => {
			let key = 'foo';

			let expected = 'bar';

			let actual = messageMap.getKey(key);

			expect(actual).to.eql(expected);
		});
	});

	describe('getMap()', () => {
		it('should return the map', () => {
			let testKey = 'foo';
			let testValue = 'bar';
			testmap.set(testKey, testValue);
			let expected = testmap;

			let actual = messageMap.getMap();

			expect(actual).to.eql(expected);
		});
	});

	describe('getMapSize()', () => {
		it('should return current map size', () => {
			let expected = 1;

			let actual = messageMap.getMapSize();

			expect(actual).to.eql(expected);
		});
	});

	describe('mapHasKey()', () => {
		it('should return if false if map dont contain key', () => {
			let key = 'bar';

			let expected = false;

			let actual = messageMap.mapHasKey(key);

			expect(actual).to.eql(expected);
		});
	});

	describe('mapHasKey()', () => {
		it('should return true if map already contains key', () => {
			let key = 'foo';

			let expected = true;

			let actual = messageMap.mapHasKey(key);

			expect(actual).to.eql(expected);
		});
	});

	describe('deleteKey()', () => {
		it('should delete key and return deleted value for key', () => {
			let key = 'foo';

			let expected = true;

			let actual = messageMap.deleteKey(key);

			expect(actual).to.eql(expected);
		});
	});

	describe('resetMap()', () => {
		it('should reset map to initial state (null)', () => {
			messageMap.resetMap();

			let expected = null;

			let actual = messageMap.getMap();

			expect(actual).to.eql(expected);
		});
	});
});