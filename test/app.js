var express = require('express');
var path = require('path');
var cookieParser = require('cookie-parser');
var logger = require('morgan');

// 중간 단계 라우터
var indexRouter = require('./routes/index');
var usersRouter = require('./routes/users');
var sendJesonRouter = require('./routes/sendJson');
var receiveJsonRouter = require('./routes/receive');


var app = express();

app.use(logger('dev'));
app.use(express.json());
app.use(express.urlencoded({ extended: false }));
app.use(cookieParser());
app.use(express.static(path.join(__dirname, 'public')));

app.use('/', indexRouter);
app.use('/users', usersRouter);
app.use('/sendJson', sendJesonRouter);
app.use('/receiveJson', receiveJsonRouter);

module.exports = app;
