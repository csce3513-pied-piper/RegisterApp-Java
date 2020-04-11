document.addEventListener("DOMContentLoaded", () => {
	//Cancel button functionality
	getCancleActionElement().addEventListener("click", clear);
		
	getSearchActionElement().addEventListener(
		"click",
		() => { window.location.assign("/search/lookupcode=" + getLookUpCode()); });	
});

function checkoutActionClick(event) {}

function getCancleActionElement(){
	return document.getElementById("cancelButton");
}

function getSearchActionElement(){
	return document.getElementById("searchButton");
}

function getLookUpCode(){
	return document.getElementById("lookup").value;
}

function clear() {
	const deleteActionUrl = ("/entity/transactionEntry/clear");
	ajaxDelete(deleteActionUrl, (callbackResponse) => {
		if (isSuccessResponse(callbackResponse)) {window.location.replace("/mainMenu");}
	});
}