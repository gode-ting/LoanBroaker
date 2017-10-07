let lint = require('mocha-eslint');

let paths = [
	'./*.js',
	'./test/*.js',
	'./src/*.js'
];

let options = [

];

lint(paths, options);