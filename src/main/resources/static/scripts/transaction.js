document.addEventListener("DOMContentLoaded", () => {
	//Cancel button functionality
	getCancleActionElement().addEventListener(
		"click",
		() => {window.location.assign("/mainMenu"); });
});

function checkoutActionClick(event) {}

function getCancleActionElement(){
	return document.getElementById("cancelButton");
}