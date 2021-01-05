const myStorage = window.localStorage
function showThreatLevel() {
	let data = JSON.parse(myStorage.getItem("object"))
	const threatLevel = data.threatLevel
	const latestTweet = data.latestTweet
	let threatLevelValue = document.getElementById("threatLevelTitle");
	let latestTweetValue = document.getElementById("latestTweet");
	threatLevelValue.innerHTML = "Your Threat Level is: " + threatLevel;
	if (latestTweet != null) {
		latestTweetValue.innerHTML = "Latest Tweet: " + latestTweet;
	} else {
		latestTweetValue.innerHTML = " ";
	}
	drawThreatMeter()
}
function drawThreatMeter() {
	let canvas = document.getElementById("canvas");
	let ctx = canvas.getContext("2d");
	ctx.canvas.width = 400;
	ctx.canvas.height = 200;
	ctx.beginPath();
	ctx.arc(200, 120, 100, Math.PI, 2 * Math.PI);
	ctx.closePath();
	ctx.beginPath();
	ctx.arc(200, 190, 150, Math.PI, 2 * Math.PI);
	//ctx.lineTo(95,50);
	ctx.closePath();
	var grd = ctx.createLinearGradient(60, 0, 400, 0);
	grd.addColorStop(0, "red");
	grd.addColorStop(0.25, "orange");
	grd.addColorStop(0.55, "yellow");
	grd.addColorStop(1, "green");
	// Fill with gradient
	ctx.fillStyle = grd;
	ctx.fill();


	ctx.stroke();

}
showThreatLevel()
