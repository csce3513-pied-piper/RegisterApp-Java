document.addEventListener("DOMContentLoaded", () => {
	getCancleActionElement().addEventListener("click", cancleActionClick);
	
	getCancleActionElement().addEventListener(
		"click",
		() => { 
		alert("In function");
		window.location.assign("/mainMenu"); });
});

function checkoutActionClick(event) {}

function cancleActionClick(event) {
	alert("In function");
	window.location.assign("/mainMenu");
}

function getCancleActionElement(){
	alert("In get function");
	return document.getElementById("cancelButton");
}