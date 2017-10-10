let lint = require('mocha-eslint');

let paths = [
	'./*.js',
	'./test/*.js',
	'./src/*.js',
	'./config/*.js'
];

let options = [

];

lint(paths, options);