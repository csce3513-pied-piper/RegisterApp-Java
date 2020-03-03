document.addEventListener("DOMContentLoaded", function(event) {
	// TODO: Anything you want to do when the page is loaded?
});

function validateForm() {
	var number = document.getElementById("ID");
	var password = document.getElementById("password");
	
	//Check for a blank password
	var numbers = /[0-9]/g;
	if(number != '' && password != ''){
		//Check for a numeric ID
		if(number.match(numbers))
			return true;
		else{
			alert("ID must be numeric!");
		}
	}
		
	else{
		alert("Please enter all credentials!");
		return false;
	}
}