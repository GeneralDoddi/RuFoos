/* 	User model
*/
var mongoose = require('mongoose');
var Schema = mongoose.Schema;

var PickupSchema = new Schema( {

	players: [String],
	full: { type: Boolean, default: false}
});

module.exports = mongoose.model('Pickups', PickupSchema);