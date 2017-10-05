import closeOnError from './closeOnError.js';

export default function (ampqConn) {
	// console.log(ampqConn);

	ampqConn.createChannel((err, ch) => {
		if (closeOnError(err, ampqConn)) {
			return;
		}
		ch.on('close', () => {
			console.log('[AMPQ] channel closed');
		});
		ch.prefetch(10);
	});
}