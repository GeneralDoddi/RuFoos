var chgpass = require('config/chgpass');
var register = require('config/register');
var login = require('config/login');
var competition = require('config/Competition.js'),
	//match = require('./Models/Match.js'),
	team = require('config/Team.js'),
	user = require('config/models'),
	pickup = require('config/Pickup.js'),
	service = require('config/service.js'),
	pickupmatch = require('config/PickupMatch.js');

module.exports = function(router) {



	router.get('/', function(req, res) {

		res.end("Node-Android-Project"); 
	});


	router.post('/users/login',function(req,res){
		var username = req.body.userName;
        	var password = req.body.password;
        		

		login.login(username,password,function (found) {
			console.log(new Date());
			console.log(found);
			res.json(found);
		});
	});


	router.post('/users/register',function(req,res){
		console.log(req.body);
		var email = req.body.email;
        	var password = req.body.password;
        		var userName = req.body.userName;
			
		register.register(email,password,userName,function (found) {
			console.log(found);
			res.json(found);
		});		
	});
	

	router.post('/users/chgpass', function(req, res) {
		var id = req.body.id;
                var opass = req.body.oldpass;
		var npass = req.body.newpass;

		chgpass.cpass(id,opass,npass,function(found){
			console.log(found);
			res.json(found);
		});	
	});


	router.post('/users/resetpass', function(req, res) {
	
		var email = req.body.email;
		
		chgpass.respass_init(email,function(found){
			console.log(found);
			res.json(found);
		});		
	});
	

	router.post('/users/resetpass/chg', function(req, res) {
	
		var email = req.body.email;
		var code = req.body.code;
		var npass = req.body.newpass;
		
		chgpass.respass_chg(email,code,npass,function(found){			
			console.log(found);
			res.json(found);
		});		
	});

	router.post('/users/playerupdate', function(req,res){
		user.findOne({'token': req.body.token},'Player', function(err, response){
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
		user.findOne({'token': req.body.token},'Player', function(err, found){
			if(err || found == null){
				console.log("User not signed up");
				res.status(401).send("Unauthorized access");
			}
			else{
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
			}
		});		
	});
	
	router.post('/teams/teamupdate', function(req,res){
		user.findOne({'token': req.body.token},'Player', function(err, response){
			if(err || response == null){
				console.log("User not signed up");
				res.status(401).send("Unauthorized access");
			}
			else{
				team.findOne({'name': req.body.name}, function(err, response){
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
			}
		});
	});
	
	router.post('/pickupmatch/signup', function(req, res){
		user.findOne({'userName': req.body.userName},'Player', function(err, response){
			if(err || response == null){
				console.log("User not signed up");
				res.status(401).send("Unauthorized access");
			}
			else{
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
			}
		});
	});

	router.post('/pickupmatch/removesignup', function(req, res){
		user.findOne({'userName': req.body.userName},'Player', function(err, response){
			if(err || response == null){
				console.log("User not signed up");
				res.status(401).send("Unauthorized access");
			}
			else{
				user.findOne({'userName': req.body.userName}, function(err, response){
					if(err || response == null){
						console.log(err);
						res.status(503).send(err);
					}
					else{
						pickup.findOne({'players': req.body.userName}, function(err, response){
							if(err || response == null){
								console.log(err);
								res.status(503).send(err);
							}
							else{
								console.log(response);
								var index = response.players.indexOf(req.body.userName);
								//console.log(index);
								response.players.splice(index,1);
								response.ready = [];
								response.full = false;
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
					}
			  	});
			}
		});
	});

	router.post('/pickupmatch/confirmpickup', function(req,res){
		user.findOne({'token': req.body.token},'Player', function(err, response){
			if(err || response == null){
				console.log("User not signed up");
				res.status(401).send("Unauthorized access");
			}
			else{
				pickup.findOne(req.body.pickupid, function(err, response){
					if(err){
						console.log(err);
						res.status(503).send(err);
					}
					else{
						response.ready.push(true);
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
			}
		});
	});

	router.post('/pickupmatch/registerteammatch', function(req, res){
		user.findOne({'token': req.body.token},'Player', function(err, response){
			if(err || response == null){
				console.log("User not signed up");
				res.status(401).send("Unauthorized access");
			}
			else{
				newMatch = new pickupmatch();
					team.findOne({'name': req.body.winnerteam}, function(err, response){
						if(err){
							console.log(err);
							res.status(503).send(err);
						}
						else{
							newMatch.winners.push(response.p1);
							user.findOne({'userName' : response.p1}, function(err,p1){
								if(err){
								console.log(err);
								res.status(503).send(err);
								}
								else{
									p1.Player.wins = p1.Player.wins + 1;
									p1.save({});
								}
							});
							newMatch.winners.push(response.p2);
							user.findOne({'userName' : response.p2}, function(err,p2){
								if(err){
								console.log(err);
								res.status(503).send(err);
								}
								else{
									p2.Player.wins = p2.Player.wins + 1;
									p2.save({});
								}
							});

							response.wins = response.wins + 1;
							newMatch.winnerteam = req.body.winnerteam;
							response.save(function(){
								team.findOne({'name': req.body.loserteam}, function(err,response){
									if(err){
										console.log(err);
										res.status(503).send(err);
									}
									else{
										newMatch.losers.push(response.p1);
										user.findOne({'userName' : response.p1}, function(err,p1){
											if(err){
												console.log(err);
												res.status(503).send(err);
											}
											else{
												p1.Player.losses = p1.Player.losses + 1;
												if(req.body.underTable == true){
													p1.Player.underTable = p1.Player.underTable + 1;
												}
												p1.save({});
											}
										});
										
										newMatch.losers.push(response.p2);
										user.findOne({'userName' : response.p2}, function(err,p2){
											if(err){
												console.log(err);
												res.status(503).send(err);
											}
											else{
												p2.Player.losses = p2.Player.losses + 1;
												if(req.body.underTable == true){
													p2.Player.underTable = p2.Player.underTable + 1;
												}
												p2.save({});
											}
										});
										response.losses = response.losses + 1;
										newMatch.loserteam = req.body.loserteam;
										if(req.body.underTable == true){
											response.underTable = response.underTable + 1;
											newMatch.underTable = true;
										}
										response.save(function(err){
											if(err){
												console.log(err);
												res.status(503).send(err);
											}
											else{
												newMatch.date = new Date();
												newMatch.save(function(err,b){
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
							});
						}
					});
			}
		});
	});

	router.post('/pickupmatch/registerquickmatch', function(req,res){
		user.findOne({'token': req.body.token},'Player', function(err, response){
			if(err || response == null){
				console.log("User not signed up");
				res.status(401).send("Unauthorized access");
			}
			else{
				newMatch = new pickupmatch();
				user.findOne({'userName' : req.body.winners[0]}, function(err,response){
					if(err){
						console.log(err);
						res.status(503).send(err);
					}
					else{
						newMatch.winners.push(req.body.winners[0]);
						console.log(newMatch.winners);
						response.Player.wins = response.Player.wins + 1;
						response.save(function(){
							user.findOne({'userName' : req.body.winners[1]}, function(err,response){
								if(err){
									console.log(err);
									res.status(503).send(err);
								}
								else{
									newMatch.winners.push(req.body.winners[1]);
									console.log(newMatch.winners);
									response.Player.wins = response.Player.wins + 1;
									response.save(function(){
										user.findOne({'userName' : req.body.losers[0]}, function(err,response){
											if(err){
												console.log(err);
												res.status(503).send(err);
											}
											else{
												newMatch.losers.push(req.body.losers[0]);
												console.log(newMatch.losers);
												response.Player.losses = response.Player.losses + 1;
												response.save(function(){
													user.findOne({'userName' : req.body.losers[1]}, function(err,response){
														if(err){
															console.log(err);
															res.status(503).send(err);
														}
														else{
															newMatch.losers.push(req.body.losers[1]);
															console.log(newMatch.losers);
															response.Player.losses = response.Player.losses + 1;
															response.save(function(){
																if(req.body.underTable === true){
																	newMatch.underTable = true;
																}
																newMatch.date = new Date();
																console.log(newMatch.winners);
																newMatch.save(function(err,b){
																	if(err){
																		console.log(err);
																		res.status(503).send(err);
																	}
																	else{
																		console.log(b);
																		res.status(201).send(b);
																		pickup.remove({ _id: req.body.pickupId }, function(err) {
																		    
																		});
																	}
																});
															});
														}
													});
												});
											}
										});
									});
								}
							});
						});
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
		user.find('userName','userName', function(err, response){
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
	

	router.get('/users/:username/matches', function(req,res){
		pickupmatch.find({ $or: [ {winners: req.params.username},{losers: req.params.username}]}, function(err, response){
			if(err){
				console.log(err);
				res.status(503).send(err);
			}
			else{
				console.log(response);
				res.status(201).send(response);
			}
		});
	});

	router.get('/users/:username/teams', function(req,res){
		team.find({$or: [ {p1: req.params.username}, {p2: req.params.username}]},function(err,response){
			if(err){
			console.log(err);
			res.status(503).send(err);
			}
			else{
				console.log(response);
				res.status(201).send(response);
			}
		})
	});
};



