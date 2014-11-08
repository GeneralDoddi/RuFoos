/* Team Model
*/
var mongoose = require('mongoose');
var Schema = mongoose.Schema;

var TeamSchema = new Schema( {
	name: {type: String, unique: true},
	p1: {type: String, required: true},
	p2: {type: String, required: true},
	wins: Number,
	losses: Number,
	underTable: Number
});

module.exports = mongoose.model('Team', TeamSchema);