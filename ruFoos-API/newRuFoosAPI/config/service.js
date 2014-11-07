
var playerfound = false;
module.exports = {
	
	setFound: function (isFound){
		playerfound = isFound;
	},
	getFound: function (){
		return playerfound;
	}
}


