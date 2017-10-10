// dependencies
import {
	fork
} from 'child_process';
import path from 'path';

// main function
export function main() {
	let connection = path.join(__dirname, 'connection.js');
	let connectionGodeTing = path.join(__dirname, 'connectionGodeTing.js');
	fork(connection);
	fork(connectionGodeTing);
}