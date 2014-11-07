/* 	User model
*/
var mongoose = require('mongoose');
var Schema = mongoose.Schema;

var PickupTestSchema = new Schema( {

	players: [String],
	full: { type: Boolean, default: false}
});

module.exports = mongoose.model('PickupTests', PickupTestSchema);