import express from 'express';
import path from 'path';

const app = express();
const __dirname = path.resolve();

const names = [
	'LoopDetect',
	'SortBitonic',
	'EvenOdd',
	'SortDLL',
	'MinStack',
	'Celeb',
	'Hanoi',
	'StockSpan',
	'PQueue',
	'SortQueue',
	'MaxWindow',
	'Permutation'
];

app.get('/:id', (req, res) => {
	try {
		res.sendFile(__dirname + '/codes/' + names[Number(req.params.id)]);
	}
	catch (err) {
		res.send('Invalid ID');
	}
});

app.get('*', (req, res) => {
	res.send('Invalid URL');
})

app.listen(3000, () => {
    console.log('Server started on port 3000');
});