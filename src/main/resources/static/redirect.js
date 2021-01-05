const myStorage = window.localStorage
function showThreatLevel() {
	let data = JSON.parse(myStorage.getItem("object"))
	const threatLevel = data.threatLevel
	const latestTweet = data.latestTweet
	alert(data.message);

	let threatLevelValue = document.getElementById("threatLevelTitle");
	let latestTweetValue = document.getElementById("latestTweet");
	threatLevelValue.innerHTML = "Your Threat Level is: " + threatLevel;
	if(latestTweet!=null){
		latestTweetValue.innerHTML = "Latest Tweet: "+latestTweet;
	}else{
		latestTweetValue.innerHTML = " ";
	}
}
showThreatLevel()