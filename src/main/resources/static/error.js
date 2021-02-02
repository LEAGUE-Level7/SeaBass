const queryString = window.location.search
const urlParams = new URLSearchParams(queryString)
const bruh = urlParams.get('type')
console.log(bruh);