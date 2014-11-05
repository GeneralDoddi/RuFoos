/* Matches model
*/
var mongoose = require('mongoose');
var Schema = mongoose.Schema;

var PickupMatchSchema = new Schema( {
	team1_id: [String],
	team2_id: [String],
	team1_score: Number,
	team2_score: Number,
	date: Date
});

module.exports = mongoose.model('PickupMatch', PickupMatchSchema);