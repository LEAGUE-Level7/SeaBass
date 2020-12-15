const myStorage = window.localStorage
function showThreatLevel() {
	let data = JSON.parse(myStorage.getItem("object"))
	console.log(data.threatLevel)
	const threatLevel = data.threatLevel
	console.log(threatLevel);
	let threatLevelValue = document.getElementById("threatLevelTitle");
	threatLevelValue.innerHTML = "your threat level is: " + threatLevel;
}
showThreatLevel()