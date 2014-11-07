/* Matches model
*/
var mongoose = require('mongoose');
var Schema = mongoose.Schema;

var PickupMatchSchema = new Schema( {
	winners: {type: [String], required: true},
	losers: {type: [String], required: true},
	winnerteam: String,
	loserteam: String,
	underTable: {type: Boolean, default: false},
	date: {type: Date, required: true}
});

module.exports = mongoose.model('PickupMatch', PickupMatchSchema);