document.addEventListener("DOMContentLoaded", () => {
	getCancleActionElement().addEventListener("click", cancleActionClick);
	
	getCancleActionElement().addEventListener(
		"click",
		() => { 
		alert("In function");
		window.location.assign("/mainMenu"); });
		
	getSearchActionElement().addEventListener(
		"click",
		() => { window.location.assign("/search/lookupcode=" + getLookUpCode()); });	
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

function getSearchActionElement(){
	return document.getElementById("searchButton");
}

function getLookUpCode(){
	return document.getElementById("lookup").value;
}