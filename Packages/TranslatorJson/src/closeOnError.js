export default function (err, ampConn) {
	if (!err) { return false; }
	console.error('AMPQ error: ', err);
	ampConn.close();
	return true;
}