let hideEmployeeSavedAlertTimer = undefined;

document.addEventListener("DOMContentLoaded", () => {
	// TODO: Things that need doing when the view is loaded
});

// Save
function saveActionClick(event) {
	// TODO: Actually save the employee via an AJAX call
	if(getFirstName() == "") {
		alert("Need First Name");
		return;
	}
	else if(getLastName() == "") {
		alert("Need Last Name");
		return;
	}
	else if(getPassword() == "") {
		alert("Need Password");
		return;
	}
	else if(getPassword() != getCPassword()) {
		alert("Password and Confirm Password need to be the same");
		return;
	}
	else if(getType() != "cashier" && getType() != "shiftmanager" && getType() != "generalmanager") {
		alert("Employee Type need to be one of the following: Cashier, Shift Manager, General Manager");
		return;
	}
	
	const saveActionElement = event.target;
	saveActionElement.disabled = true;

	const firstName = getFirstName();
	const firstNameIsDefined = (firstName != null);
	const saveActionUrl = ("/api/employee/"
		+ (firstNameIsDefined ? firstName : ""));
	const saveProductRequest = {
		firstName: getFirstName(),
		lastName: getLastName(),
		password: getPassword()
	};

	if (firstNameIsDefined) {
		ajaxPut(saveActionUrl, saveProductRequest, (callbackResponse) => {
			saveActionElement.disabled = false;

			if (isSuccessResponse(callbackResponse)) {
				displayEmploSavedAlertModal();
			}
		});
	} else {
		ajaxPost(saveActionUrl, saveProductRequest, (callbackResponse) => {
			saveActionElement.disabled = false;

			if (isSuccessResponse(callbackResponse)) {
				displayEmploSavedAlertModal();

				if ((callbackResponse.data != null)) {

					document.getElementById("deleteActionContainer").classList.remove("hidden");

					setFirstName(callbackResponse.data);
				}
			}
		});
	}
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
function getSaveActionElement() {
	return document.getElementById("saveButton");
}

function getSavedAlertModalElement() {
	return document.getElementById("employeeSavedAlertModal");
}
function getDeleteActionElement() {
	return document.getElementById("deleteButton");
}

function getFirstName() {
	return document.getElementById("firstname").value;
}
function setFirstName(firstName) {
	document.getElementById("firstname").value = firstName;
}

function getLastName() {
	return document.getElementById("lastname").value;
}
function setLastName(lastName) {
	document.getElementById("lastname").value = lastName;
}

function getPassword() {
	return document.getElementById("password").value;
}
function setPassword(pass) {
	document.getElementById("password").value = pass;
}

function getCPassword() {
	return document.getElementById("cpassword").value;
}
function setCPassword(cpass) {
	document.getElementById("cpassword").value = cpass;
}

function getType() {
	return document.getElementById("type").value;
}
function setType(type) {
	document.getElementById("type").value = type;
}
//End getters and setters