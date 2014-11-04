/* Matches model
*/
var mongoose = require('mongoose');
var Schema = mongoose.Schema;

var MatchSchema = new Schema( {
	team1_id: Schema.Types.ObjectId,
	team2_id: Schema.Types.ObjectId,
	team1_score: Number,
	team2_score: Number,
	date: Date,
	comp_id: Schema.Types.ObjectId
});

module.exports = mongoose.model('Match', MatchSchema);