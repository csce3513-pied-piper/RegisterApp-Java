let hideEmployeeSavedAlertTimer = undefined;

document.addEventListener("DOMContentLoaded", () => {
	// TODO: Things that need doing when the view is loaded
	document.getElementById("Save").addEventListener("click", saveFunction);
	function saveFunction(){
	var firstname = document.getElementById("firstname").value;
	var lastname = document.getElementById("lastname").value;
	var password = document.getElementById("password").value;
	var cpassword = document.getElementById("cpassword").value;
	if ((firstname == null)or(lastname == null)or(password == null)or(password !== cpassword)){
	alert ("Error");
	return;}
	//TODO: AJAX Post to server?
	}
});

// Save
function saveActionClick(event) {
	// TODO: Actually save the employee via an AJAX call
	displayEmployeeSavedAlertModal();
}

function displayEmployeeSavedAlertModal() {
	if (hideEmployeeSavedAlertTimer) {
		clearTimeout(hideEmployeeSavedAlertTimer);
	}

	const savedAlertModalElement = getSavedAlertModalElement();
	savedAlertModalElement.style.display = "none";
	savedAlertModalElement.style.display = "block";

	hideEmployeeSavedAlertTimer = setTimeout(hideEmployeeSavedAlertModal, 1200);
}

function hideEmployeeSavedAlertModal() {
	if (hideEmployeeSavedAlertTimer) {
		clearTimeout(hideEmployeeSavedAlertTimer);
	}

	getSavedAlertModalElement().style.display = "none";
}
// End save

//Getters and setters
function getSavedAlertModalElement() {
	return document.getElementById("employeeSavedAlertModal");
}
//End getters and setters
