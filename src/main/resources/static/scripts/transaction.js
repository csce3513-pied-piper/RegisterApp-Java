document.addEventListener("DOMContentLoaded", () => {
	getCheckoutActionElement().addEventListener("click", saveTransaction);
	//Cancel button functionality
	getCancleActionElement().addEventListener("click", clear);
		
	getSearchActionElement().addEventListener(
		"click",
		() => { window.location.assign("/search/lookupcode=" + getLookUpCode()); });

	const productListElements = document.getElementById("productsListing").children;

	for (let i = 0; i < productListElements.length; i++) {
		const transactionEntryIdList = productListElements[i].getElementsByClassName("transactionEntryId");
		const deleteBtnList = productListElements[i].getElementsByClassName("deleteBtn");
		const plusBtnList = productListElements[i].getElementsByClassName("plusBtn");
		const minusBtnList = productListElements[i].getElementsByClassName("minusBtn");
		for (let j = 0; j < transactionEntryIdList.length; j++) {
			deleteBtnList[j].addEventListener(
				"click",
				() => {
					var deleteActionUrl = ("/entity/transactionEntry/delete/" + transactionEntryIdList[j].value);

					ajaxDelete(deleteActionUrl, (callbackResponse) => {
						if (isSuccessResponse(callbackResponse)) {
							window.location.replace("/transactionMenu");
						}
					});
				});

			plusBtnList[j].addEventListener(
				"click",
				() => {
					var saveActionUrl = ("/entity/transactionEntry/plus/" + transactionEntryIdList[j].value);
					var saveTransactionEntryRequest = {
						quantity: 1
					};

					ajaxPut(saveActionUrl, saveTransactionEntryRequest, (callbackResponse) => {
						if (isSuccessResponse(callbackResponse)) {
							window.location.replace("/transactionMenu");
						}
					});
				});

			minusBtnList[j].addEventListener(
				"click",
				() => {
					var saveActionUrl = ("/entity/transactionEntry/minus/" + transactionEntryIdList[j].value);
					var saveTransactionEntryRequest = {
						quantity: -1
					};

					ajaxPut(saveActionUrl, saveTransactionEntryRequest, (callbackResponse) => {
						if (isSuccessResponse(callbackResponse)) {
							window.location.replace("/transactionMenu");
						}
					});
				});
		}
	}
})
displayTotal();

function saveTransaction() {
	const cashierId = document.getElementById("employeeId").value;
	const transactionReferenceId = document.getElementById("transactionReferenceId").value;
	const saveActionUrl = ("/entity/transactionEntry/save/" + cashierId + "/" + transactionReferenceId);
	const saveTransactionRequest = {
		total: 0
	};
	ajaxPost(saveActionUrl, saveTransactionRequest, (callbackResponse) => {
		if (isSuccessResponse(callbackResponse)) {window.location.replace("/transactionMenu");}
	});
}

function displayTotal() {
	const productListElements = document.getElementById("productsListing").children;
	var total = 0;

	for (let i = 0; i < productListElements.length; i++) {
		var priceList = productListElements[i].getElementsByClassName("price");
		for (let j = 0; j < priceList.length; j++) {
			total = parseInt(total) + parseInt(priceList[j].value);
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

function getCheckoutActionElement(){
	return document.getElementById("checkoutButton");
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