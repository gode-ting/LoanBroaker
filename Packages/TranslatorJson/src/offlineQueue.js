
let offlineQueue = [];

module.exports = {
	QueueElement,
	pushToOfflineQueue,
	getOfflineQueue
}

function getOfflineQueue () {
	return offlineQueue;
}

function pushToOfflineQueue (element) {
	offlineQueue.push(element);
}

function QueueElement (content) {
	this.content = content;
}

function createOfflineQueue () {
	return [];
}