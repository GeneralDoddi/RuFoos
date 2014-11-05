
/**
 * Module dependencies.
 */
var express  = require('express');
var connect = require('connect');
var bodyParser = require('body-parser');
var app      = express();
var router = express.Router();
var port     = process.env.PORT || 10000;

// Configuration
app.use(express.static(__dirname + '/public'));
app.use(connect.logger('dev'));
app.use(bodyParser.urlencoded({extended: true}));
app.use(bodyParser.json());

// Routes
app.use('/api', router);
require('./routes/routes.js')(router);

app.listen(port);

console.log('The App runs on port ' + port);
