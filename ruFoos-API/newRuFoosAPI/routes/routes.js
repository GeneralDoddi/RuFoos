var chgpass = require('config/chgpass');
var register = require('config/register');
var login = require('config/login');
var competition = require('config/Competition.js'),
	//match = require('./Models/Match.js'),
	team = require('config/Team.js'),
	user = require('config/models'),
	pickup = require('config/Pickup.js'),
	service = require('config/service.js');

module.exports = function(router) {



	router.get('/', function(req, res) {

		res.end("Node-Android-Project"); 
	});


	router.post('/login',function(req,res){
		var email = req.body.email;
        	var password = req.body.password;
        		

		login.login(email,password,function (found) {
			console.log(found);
			res.json(found);
	});
	});


	router.post('/register',function(req,res){
		console.log(req.body);
		var email = req.body.email;
        	var password = req.body.password;
        		var userName = req.body.userName;
			
		register.register(email,password,userName,function (found) {
			console.log(found);
			res.json(found);
	});		
	});
	

	router.post('/chgpass', function(req, res) {
		var id = req.body.id;
                var opass = req.body.oldpass;
		var npass = req.body.newpass;

		chgpass.cpass(id,opass,npass,function(found){
			console.log(found);
			res.json(found);
	});	
	});


	router.post('/resetpass', function(req, res) {
	
		var email = req.body.email;
		
		chgpass.respass_init(email,function(found){
			console.log(found);
			res.json(found);
	});		
	});
	

	router.post('/resetpass/chg', function(req, res) {
	
		var email = req.body.email;
		var code = req.body.code;
		var npass = req.body.newpass;
		
		chgpass.respass_chg(email,code,npass,function(found){			
			console.log(found);
			res.json(found);
	});		
	});

	router.post('/users/playerupdate', function(req,res){
	user.findOne({'userName': req.body.userName},'Player', function(err, response){
		if(err){
			console.log("Error: " + err);
			res.status(503).send(err);
		}
		else{
			console.log(response);
			//var parsedResponse = JSON.parse(response[0]);
			if(req.body.win != 0){
				response.Player.wins = response.Player.wins + 1;
			}
			
			if(req.body.loss != 0){
				response.Player.losses = response.Player.losses + 1;
			}
			if(req.body.underTable != 0){
				response.Player.underTable = response.Player.underTable + 1;
			}
			response.save(function(err,b){
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
	console.log(req.body);

	if(req.body.p1 === req.body.p2){
		res.status(503).send("The same player cannot be signed to the same team");
	}
	else{
		user.find({'userName': req.body.p1},'userName, Player', function(err, response){
			if(err){
				console.log("Error: " + err);
				res.status(503).send(err);
			}
			else{
				
				newTeam.p1 = req.body.p1;

				user.find({'userName': req.body.p2},'userName, Player', function(err, response){
					if(err){
						console.log("Error: " + err);
						res.status(503).send(err);
					}
					else{
						
						newTeam.p2 = req.body.p2;

						newTeam.name = req.body.name;
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

router.post('/teams/teamupdate', function(req,res){
	teams.findOne({'name': req.body.name}, function(err, response){
		if(err){
			console.log("Error: " + err);
			res.status(503).send(err);
		}
		else{
			if(req.body.win != 0){
				response.wins = response.wins + 1;
			}
			if(req.body.loss != 0){
				response.losses = response.losses + 1;
			} 
			if(req.body.underTable != 0){
				response.undertable = response.underTable + 1;
			}
			response.save(function(err,b){
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

router.post('/pickupmatch/signup', function(req, res){
	var newPickup = new pickup();
		user.find({'userName': req.body.userName}, function(err, response){
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
					service.setFound(false);
					response.forEach(function(pickupMatch){

						if(pickupMatch.players.indexOf(req.body.userName) != -1){
							res.status(503).send("Player aldready signed up");
							service.setFound(true);
						}
						else{
							//console.log(pickupMatch.players.length)
							if(pickupMatch.players.length < 4){
								pickupMatch.players.push(req.body.userName);
								if(pickupMatch.players.length == 4){
									pickupMatch.full = true;
								}
							}
								service.setFound(true);
								pickupMatch.save(function(err, b){
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
					//console.log(service.getFound())
					if(!service.getFound()){
						console.log("inserting");
						newPickup.players.push(req.body.userName);
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
	user.find('userName','userName email', function(err, response){
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
};



