function modifyItem() {
    
    var items = document.querySelectorAll(".item_gallery");
    
    for(var i = 0; i < items.length; i++) {
        addClick(items[i]);
    }
}

function addClick(blogItem) {
    var dataId = blogItem.getAttribute('data-id');
    blogItem.addEventListener('click', function(event) {
                              window.Screens.postMessage("gallery-item", dataId);
                              });
} 

modifyItem();
