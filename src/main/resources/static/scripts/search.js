document.addEventListener("DOMContentLoaded", () => {
    const productListElements = document.getElementById("productsListing").children;

    for (let i = 0; i < productListElements.length; i++) {
        productListElements[i].addEventListener("click", saveActionClick);
    }
});

function findClickedListItemElement(clickedTarget) {
    if (clickedTarget.tagName.toLowerCase() === "li") {
        return clickedTarget;
    } else {
        let ancestorIsListItem = false;
        let ancestorElement = clickedTarget.parentElement;

        while (!ancestorIsListItem && (ancestorElement != null)) {
            ancestorIsListItem = (ancestorElement.tagName.toLowerCase() === "li");

            if (!ancestorIsListItem) {
                ancestorElement = ancestorElement.parentElement;
            }
        }

        return (ancestorIsListItem ? ancestorElement : null);
    }
}

function saveActionClick(event) {
    let listItem = findClickedListItemElement(event.target);
    const productId = listItem.querySelector("input[name='productId'][type='hidden']").value;

    const saveActionUrl = ("/entity/transactionEntry/");
    const saveTransactionEntryRequest = {
        productid: productId
    };

    ajaxPost(saveActionUrl, saveTransactionEntryRequest, (callbackResponse) => {
        if (isSuccessResponse(callbackResponse)) {window.location.replace("/transactionMenu?=" + productId);}
    });
}