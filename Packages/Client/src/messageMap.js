module.exports = {
	createMap,
	getMap,
	getKey,
	deleteKey,
	addToMap,
	getMapSize,
	resetMap,
	mapHasKey
};

let messageMap = null;

function createMap() {
	messageMap = new Map();
	return true;
}

function getMap() {
	return messageMap;
}

function getKey(key) {
	return messageMap.get(key);
}

function deleteKey(key) {
	return messageMap.delete(key);
}

function addToMap(key, value) {
	if (mapHasKey(key)) {
		console.log('Map already contains key ', key);
		return false;
	} else {
		messageMap.set(key, value);
		return true;
	}
}

function getMapSize() {
	return messageMap.size;
}

function resetMap() {
	messageMap = null;
}

function mapHasKey(key) {
	return messageMap.has(key);
}