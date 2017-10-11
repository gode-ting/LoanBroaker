export default function () {
	let date = new Date();
	let timeStamp = `${date.getHours()}:${date.getMinutes()}:${date.getSeconds()}`;

	return timeStamp;
}