let hideEmployeeSavedAlertTimer = undefined;

document.addEventListener("DOMContentLoaded", () => {
	// TODO: Things that need doing when the view is loaded
	const employeeId = getEmployeeID();

	document.getElementById("firstname").addEventListener("keypress", firstNameKeypress);
	document.getElementById("employeeEmployeeId").addEventListener("keypress", employeeIdKeypress);

	getSaveActionElement().addEventListener("click", saveActionClick);
	getDeleteActionElement().addEventListener("click", deleteActionClick);

	if (!productLookupCodeElement.disabled) {
		productLookupCodeElement.focus();
		productLookupCodeElement.select();
	}
});

function employeeIdKeypress(event) {
	if (event.which !== 13) { // Enter key
		return;
	}

	const firstNameElement = document.getElementById("firstname");
	firstNameElement.focus();
	firstNameElement.select();
}

function firstNameKeypress(event) {
	if (event.which !== 13) { // Enter key
		return;
	}

	saveActionClick();
}

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

	const employeeId = getEmployeeID();
	const IDIsDefined = (employeeId != null && (employeeId.trim() !== ""));
	const saveActionUrl = ("/api/employee/" + (IDIsDefined ? id : ""));
	const saveProductRequest = {
		id: getID(),
		employeeId: getEmployeeID(),
		firstName: getFirstName(),
		lastName: getLastName(),
		password: getPassword()
	};

	if (IDIsDefined) {
		ajaxPut(saveActionUrl, saveProductRequest, (callbackResponse) => {
			saveActionElement.disabled = false;

			if (isSuccessResponse(callbackResponse)) {
				displayEmployeeSavedAlertModal();
			}
		});
	} else {
		ajaxPost(saveActionUrl, saveProductRequest, (callbackResponse) => {
			saveActionElement.disabled = false;

			if (isSuccessResponse(callbackResponse)) {
				displayEmployeeSavedAlertModal();

				if ((callbackResponse.data != null)) {

					document.getElementById("deleteActionContainer").classList.remove("hidden");

					setId(callbackResponse.data.id.trim());
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

function getID() {
	return document.getElementById("employeeId").value;
}
function setID(employeeId) {
	document.getElementById("employeeId").value = employeeId;
}
function getManagerID() {
	return document.getElementById("employeeManagerId").value;
}
function setManagerID(employeeManagerId) {
	document.getElementById("employeeManagerId").value = employeeManagerId;
}
function getEmployeeID() {
	return document.getElementById("employeeEmployeeId").value;
}
function setEmployeeID(employeeEmployeeId) {
	document.getElementById("employeeEmployeeId").value = employeeEmployeeId;
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