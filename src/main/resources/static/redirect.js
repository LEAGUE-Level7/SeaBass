(function showThreatLevel (){
    const urlParams = new URLSearchParams(document.location.search);
    const threatLevel = urlParams.get('threatlevel')
    console.log(threatLevel);

    let threatLevelValue = document.getElementById("threatLevelTitle");

    threatLevelValue.innerHTML = "your threat level is: " + threatLevel;
}) ();