module.exports = {
	createMap,
	getMap,
	addToMap,
	getMapSize,
	resetMap
};

let messageMap = null;

function createMap () {
	messageMap = new Map();
	return true;
}

function getMap () {
	return messageMap;
}

function addToMap (key, value) {
	if (messageMap.has(key)) {
		return false;
	}
	messageMap.set(key, value);
	return true;
}

function getMapSize () {
	return messageMap.size;
}

function resetMap() {
	messageMap = null;
}