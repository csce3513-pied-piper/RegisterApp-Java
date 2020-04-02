document.addEventListener("DOMContentLoaded", () => {
	const productLookupCodeElement = getProductLookupCodeElement();
	
	productLookupCodeElement.addEventListener("keypress", productLookupCodeKeypress);
	
	getSaveActionElement().addEventListener("click", checkoutActionClick);
	getDeleteActionElement().addEventListener("click", cancleActionClick);
});

function checkoutActionClick(event) {}

function cancelActionClick(event) {}