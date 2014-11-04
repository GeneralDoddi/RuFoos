/*
* RuFoos API
*
*
*/

var express = require('express'),
	bodyParser = require('body-parser'),
	mongoose = require('mongoose'),
	cors =require('cors'),
	competition = require('./Models/Competition.js'),
	match = require('./Models/Match.js'),
	team = require('./Models/Team.js'),
	user = require('./Models/User.js'),
	pickup = require('./Models/Pickup.js')
	service = require('./Services/service.js');

var app = express();
var port = process.env.PORT || 10000;
var router = express.Router();
var ObjectId = mongoose.Types.ObjectId;

//configure app to use body parser
app.use(bodyParser.urlencoded({extended: true}));
app.use(bodyParser.json());
//allow origin
app.use(cors());

var connectMongo = function (){
	mongoose.connect('mongodb://localhost/test', {keepAlive: 1});
	console.log('Connecting to mongodb');
};

//mongoose.connection.on('disconnected', connectMongo);
connectMongo();

//Use middleware
router.use(function(req, res, next)
{
	console.log("Routing in progress");
	next();
});


/*
*	ROUTES
*/
router.get('/', function(req, res)
{
	res.json({message: 'This is a test message, please brace yourself!'});
});

// RuFoos POST methods:

router.post('/users/adduser', function(req,res){
	var newUser = new user();

	//User information inserted
	newUser.userName = req.body.username;
	newUser.email = req.body.email;
	newUser.password = req.body.password;
	// child object Player mounted up
		newUser.Player.wins = 0;
		newUser.Player.losses = 0;
		newUser.Player.underTable = 0;

	newUser.save(function(err,b){
		if(err){
			console.log(err);
			res.status(503).send(err);
		}
		else{
			res.status(201).send(b);
		}
	});
});

router.post('/users/playerupdate', function(req,res){
	user.find({'userName': req.body.username},'Player', function(err, response){
		if(err){
			console.log("Error: " + err);
			res.status(503).send(err);
		}
		else{
			console.log(response);
			//var parsedResponse = JSON.parse(response[0]);
			if(req.body.win != undefined){
				response[0].Player.wins = response[0].Player.wins + 1;
				console.log(response[0].Player.wins);
			}
			
			if(req.body.loss != undefined){
				response[0].Player.losses = response[0].Player.losses + 1;
			}
			if(req.body.underTable != undefined){
				response[0].Player.underTable = response[0].Player.underTable + 1;
			}
			response[0].save(function(err,b){
				if(err){
					console.log(err);
					res.status(503).send(err);
				}
				else{
					console.log(b);
					res.status(201).send(b);
				}
			});

		}
	});
});

router.post('/teams/addteam', function(req,res){
	var newTeam = new team();

	if(req.body.player1 === req.body.player2){
		res.status(503).send("The same player cannot be signed to the same team");
	}
	else{
		user.find({'userName': req.body.player1},'userName, Player', function(err, response){
			if(err){
				console.log("Error: " + err);
				res.status(503).send(err);
			}
			else{
				
				newTeam.p1 = req.body.player1;

				user.find({'userName': req.body.player2},'userName, Player', function(err, response){
					if(err){
						console.log("Error: " + err);
						res.status(503).send(err);
					}
					else{
						
						newTeam.p2 = req.body.player2;

						newTeam.name = req.body.teamname;
						newTeam.wins = 0;
						newTeam.losses = 0;
						newTeam.underTable = 0;
						
						newTeam.save(function(err,b){
							if(err){
										console.log(err);
										res.status(503).send(err);
									}
									else{
										console.log(b);
										res.status(201).send(b);
									}
						});
					}
				});
			}
		});
	}

});

router.post('/pickupmatch/signup', function(req, res){
	var newPickup = new pickup();
		user.find({'userName': req.body.player}, function(err, response){
			if(err){
				console.log("Error: " + err);
				res.status(503).send(err);
			}
			else{
				pickup.find('',function(err,response){
				if(err){
					console.log("Error: " + err);
					res.status(503).send(err);
				}
				else{
					response.forEach(function(pickupMatch){
						service.setFound(false);
						if(!pickupMatch.full){
							if( req.body.player == pickupMatch.player1 || 
								req.body.player == pickupMatch.player2 ||
								req.body.player == pickupMatch.player3 ||
								req.body.player == pickupMatch.player4)
							{
								res.status(503).send("Player aldready signed up");
								service.setFound(true);
							}
							else{

								if(pickupMatch.player1 == undefined){
									pickupMatch.player1 = req.body.player;
								}
								else if(pickupMatch.player2 == undefined){
									pickupMatch.player2 = req.body.player;
								}
								else if(pickupMatch.player3 == undefined){
									pickupMatch.player3 = req.body.player;
								}
								else if(pickupMatch.player4 == undefined){
									pickupMatch.player4 = req.body.player;
									pickupMatch.full = true;
								}
								pickupMatch.save(function(err, b){
									service.setFound(true);
									if(err){
										console.log(err);
										res.status(503).send(err);
									}
									else{
										console.log(b);
										res.status(201).send(b);
									}
								});
							}
						}		
					});
					if(!service.getFound()){
						console.log("inserting");
						newPickup.player1 = req.body.player;
						newPickup.save(function(err, b){
							if(err){
								console.log(err);
								res.status(503).send(err);
							}
							else{
								console.log(b);
								res.status(201).send(b);
							}
						});
					}
				}
		  	});
		}
	});
});

// RuFoos GET Methods: 

router.get('/users/getuserbyname/:username', function(req, res){
	user.findOne({'userName' : req.params.username},'userName email',function(err, response){
		if(err){
			console.log("Error: " + err);
			res.status(503).send(err);
		}
		else{
			console.log(response);
			res.json(response);
		}
	});
});

router.get('/users/getplayerinfo/:playername', function(req,res){
	user.findOne({'userName' : req.params.playername},'Player',function(err, response){
		if(err){
			console.log("Error: " + err);
			res.status(503).send(err);
		}
		else{
			console.log(response);
			res.json(response);
		}
	});
});

router.get('/teams/getteambyname/:teamname', function(req, res){
	team.findOne({'name' : req.params.teamname}, function(err,response){
		if(err){
			console.log("Error: " + err);
			res.status(503).send(err);
		}
		else{
			console.log(response);
			res.json(response);
		}
	});
});

router.get('/teams/getallteams', function(req, res){
	team.find('name', function(err, response){
		if(err){
			console.log("Error: " + err);
			res.status(503).send(err);
		}
		else{
			console.log(response);
			res.json(response);
		}
	});
});

router.get('/users/getallusers', function(req, res){
	user.find('userName','userName email Player', function(err, response){
		if(err){
			console.log("Error: " + err);
			res.status(503).send(err);
		}
		else{
			console.log(response);
			res.json(response);
		}
	});
});

router.get('/pickupmatch/getpickupmatch/:id', function(req,res){
	pickup.findById(req.params.id, function(err, response){
		if(err){
			console.log("Error: " + err);
			res.status(503).send(err);
		}
		else{
			console.log(response);
			res.json(response);
		}
	});
});

/*
	Routes:

		POST METHODS:
			/users/adduser
				parameters: username, email, password
			/users/playerupdate
				parameters: win, loss, underTable
			/teams/addteam
				parameters: teamname, player1, player2
			/pickupmatch/signup
				parameters: player
		
		GET METHODS:
			/users/getuserbyname/{username}
			/users/getallusers
			/teams/getteambyname/{teamname}
			/teams/getallteams

*/

app.use('/api', router);
app.listen(port);
console.log("Server listening on: " + port);