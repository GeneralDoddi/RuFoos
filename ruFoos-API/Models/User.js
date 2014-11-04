/* 	User model
*/
var mongoose = require('mongoose');
var Schema = mongoose.Schema;

var UserSchema = new Schema( {

	userName : {type: String, unique: true},
	email : {type: String, required: true},
	password : {type: String, required: true},
	Player: {
		wins: Number,
		losses: Number,
		underTable: Number
	}
});

module.exports = mongoose.model('User', UserSchema);