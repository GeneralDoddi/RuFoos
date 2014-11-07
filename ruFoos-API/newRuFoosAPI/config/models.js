var mongoose = require('mongoose');

var Schema = mongoose.Schema;

var userSchema = mongoose.Schema({ 
	userName: {type:String, unique: true},
	token : String,
	email: String, 
	hashed_password: String, 
	salt : String,
	temp_str:String,
	Player: {
		wins: Number,
		losses: Number,
		underTable: Number
	}
});

mongoose.connect('mongodb://localhost:27017/node-android');
module.exports = mongoose.model('users', userSchema);        
