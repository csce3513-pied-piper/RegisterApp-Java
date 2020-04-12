document.addEventListener("DOMContentLoaded", () => {
	//Cancel button functionality
	getCancleActionElement().addEventListener("click", clear);
		
	getSearchActionElement().addEventListener(
		"click",
		() => { window.location.assign("/search/lookupcode=" + getLookUpCode()); });	
})
displayTotal();

function checkoutActionClick(event) {}

function displayTotal() {
	const productListElements = document.getElementById("productsListing").children;
	var total = 0;

	for (let i = 0; i < productListElements.length; i++) {
		var priceList = productListElements[i].getElementsByClassName("price");
		for (let j = 0; j < priceList.length; j++) {
			total = total + priceList[j].value;
		}
	}

	total = total/100;
	total = total.toFixed(2);
	document.getElementById("total").innerHTML = "$" + total;
}

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