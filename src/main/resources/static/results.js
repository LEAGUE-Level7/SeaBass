const myStorage = window.localStorage
let currentThreatLevel = 0
let threatLevel = 0;
function showThreatLevel() {
	let data = JSON.parse(myStorage.getItem("object"))
	threatLevel = data.threatLevel
	const latestTweet = data.latestTweet
	let threatLevelValue = document.getElementById("threatLevelTitle");
	let latestTweetValue = document.getElementById("latestTweet");
	threatLevelValue.innerHTML = "Your Threat Level is: " + threatLevel;
	if (latestTweet == undefined) {
		latestTweetValue.innerHTML = " ";
	} else {
		latestTweetValue.innerHTML = "Latest Tweet: " + latestTweet;
		
	}
	drawThreatMeter(threatLevel)
}
function drawThreatMeter() {
	let canvas = document.getElementById("canvas");
	let ctx = canvas.getContext("2d");
	ctx.canvas.width = 400;
	ctx.canvas.height = 200;
	ctx.arc(200, 190, 150, Math.PI, 2 * Math.PI);
	//ctx.lineTo(95,50);
	var grd = ctx.createLinearGradient(60, 0, 400, 0);
	grd.addColorStop(0, "green");
	grd.addColorStop(0.25, "yellow");
	grd.addColorStop(0.55, "orange");
	grd.addColorStop(1, "red");
	// Fill with gradient
	ctx.fillStyle = grd;
	ctx.fill();
	let deg = Math.PI;
	let start = Math.PI+Math.PI/2;
	let radius = 150;
	
	let maxThreatLevel = 10;
	deg=start-currentThreatLevel*Math.PI/maxThreatLevel
	ctx.beginPath();
	ctx.moveTo(200,190)
	ctx.lineTo(200+Math.sin(deg)*radius, 190+Math.cos(deg)*radius)
	ctx.closePath();
	ctx.strokeStyle = "black";
	ctx.lineWidth = 10
	ctx.stroke();
	ctx.beginPath();
	ctx.arc(200+Math.sin(deg)*radius, 190+Math.cos(deg)*radius, 5, 0, 2*Math.PI)
	ctx.strokeStyle = "red";
	ctx.lineWidth = 10
	ctx.stroke();
	currentThreatLevel+=0.07
	if(currentThreatLevel < threatLevel){
		window.requestAnimationFrame(drawThreatMeter)
		
	}
}

showThreatLevel()
