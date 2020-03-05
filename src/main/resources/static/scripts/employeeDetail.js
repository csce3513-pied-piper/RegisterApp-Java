let hideEmployeeSavedAlertTimer = undefined;

document.addEventListener("DOMContentLoaded", () => {
	// TODO: Things that need doing when the view is loaded
});

// Save
function saveActionClick(event) {
	// TODO: Actually save the employee via an AJAX call
	var firstName = document.getElementById("firstname").value;
	var lastName = document.getElementById("lastname").value;
	var pass = document.getElementById("password").value;
	var confirmPass = document.getElementById("cpassword").value;
	var employeeType = document.getElementById("type").value;
	
	if(firstName == "") {
		alert("Need First Name");
		return;
	}
	else if(lastName == "") {
		alert("Need Last Name");
		return;
	}
	else if(pass == "") {
		alert("Need Password");
		return;
	}
	else if(pass != confirmPass) {
		alert("Password and Confirm Password need to be the same");
		return;
	}
	else if(employeeType != "cashier" && employeeType != "shiftmanager" && employeeType != "generalmanager") {
		alert("Employee Type need to be one of the following: Cashier, Shift Manager, General Manager");
		return;
	}
	displayEmployeeSavedAlertModal();
	ajaxPatch(resourceRelativeUri, data, callback);
	ajaxPost(resourceRelativeUri, data, callback);
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