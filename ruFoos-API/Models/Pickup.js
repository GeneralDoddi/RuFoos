/* 	User model
*/
var mongoose = require('mongoose');
var Schema = mongoose.Schema;

var PickupSchema = new Schema( {

	player1: String,
	player2: String,
	player3: String,
	player4: String,
	full: { type: Boolean, default: false}
});

module.exports = mongoose.model('Pickups', PickupSchema);