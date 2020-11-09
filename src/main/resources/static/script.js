alert("hello");

function makeMessage(){
	alert("you clicked the button!");
	postData('http://localhost:8080', { answer: 42 })
	  .then(data => {
	    console.log(data); // JSON data parsed by `data.json()` call
	  });
}


//Example POST method implementation:
async function postData(url = '', data = {}) {
  // Default options are marked with *
  const response = await fetch(url, {
    method: 'POST', // *GET, POST, PUT, DELETE, etc.
    headers: {
      'Content-Type': 'text/plain'
      // 'Content-Type': 'application/x-www-form-urlencoded',
    },
    body: JSON.stringify(data) // body data type must match "Content-Type" header
  });
  return response.json(); // parses JSON response into native JavaScript objects
}


