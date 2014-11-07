/* Matches model
*/
var mongoose = require('mongoose');
var Schema = mongoose.Schema;

var CompetitionMatchSchema = new Schema( {
	team1_id: {type: String, required: true},
	team2_id: {type: String, required: true},
	team1_score: Number,
	team2_score: Number,
	date: Date,
	comp_id: {type: String, required: true},
});

module.exports = mongoose.model('CompetitionMatch', CompetitionMatchSchema);