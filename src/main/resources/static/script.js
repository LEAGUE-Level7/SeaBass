let bruh = {};
const myStorage = window.localStorage

const sampleForm = document.getElementById("sampleform");
if (sampleForm) {
	let accountInputField = document.getElementById("target");
	let collectDataCheckbox = document.getElementById("collectDataCheckBox");
	sampleForm.addEventListener("submit", (event) => {
		event.preventDefault();
		console.log("form submitted");
	    postData('http://localhost/getScore', { username: accountInputField.value, collectdata: collectDataCheckBox.checked })
			.then(data => {
				bruh = data;
				console.log(data);
				myStorage.setItem("object", JSON.stringify(data))
				window.location.href = "http://localhost/redirect";// JSON data parsed by `data.json()` call
			});

	})
}


//Example POST method implementation:
async function postData(url = '', data = {}) {
	// Default option;s are marked with *
	console.log(data);

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


