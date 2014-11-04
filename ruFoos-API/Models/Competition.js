/* Competition/Leagu model
*/
var mongoose = require('mongoose');
var Schema = mongoose.Schema;

var CompetitionSchema = new Schema( {
	name: {type: String, unique: true },
	startDate: Date,
	endDate: Date,
	teams: [Schema.Types.ObjectId],
	matches: [Schema.Types.ObjectId]
});

module.exports = mongoose.model('Competition', CompetitionSchema);