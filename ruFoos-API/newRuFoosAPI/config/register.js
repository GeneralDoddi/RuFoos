var crypto = require('crypto');
var rand = require('csprng');
var mongoose = require('mongoose');
var user = require('config/models');



exports.register = function(email,password,userName,callback) {

var x = email;
if(!(x.indexOf("@")<1 || x.lastIndexOf(".")<x.indexOf("@")+2 || x.lastIndexOf(".")+2>=x.length)){
if (password.length > 4) {

var temp =rand(160, 36);
var newpass = temp + password;
var token = crypto.createHash('sha512').update(email +rand).digest("hex");
var hashed_password = crypto.createHash('sha512').update(newpass).digest("hex");

var newuser = new user({ 
	userName: userName,
	token: token,
	email: email, 
	hashed_password: hashed_password,
	salt :temp,
	Player: {
		wins: 0,
		losses: 0,
		underTable: 0
	} });

user.find({email: email},function(err,users){

var len = users.length;

if(len == 0){
 	newuser.save(function (err) {
	
	callback({'response':"Sucessfully Registered"});
		
});
}else{

	callback({'response':"Email already Registered"});

}});}else{

	callback({'response':"Password Weak"});
	
}}else{

	callback({'response':"Email Not Valid"});
	
}
}

