
let offlineQueue = [];

module.exports = {
	QueueElement,
	pushToOfflineQueue,
	getOfflineQueue,
	shiftOfflineQueue
};

function shiftOfflineQueue () {
	return offlineQueue.shift();
}

function getOfflineQueue () {
	return offlineQueue;
}

function pushToOfflineQueue (element) {
	offlineQueue.push(element);
	return true;
}

function QueueElement (content) {
	this.content = content;
}
