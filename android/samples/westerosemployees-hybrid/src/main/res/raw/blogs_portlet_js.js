function modifyItem() {

    var items = document.querySelectorAll(".item-blog");

    for(var i = 0; i < items.length; i++) {
        items[i].style = null;
        addClick(items[i]);
        if(i != items.length - 1){
              addSeparator(items[i]);
        }
    }
}

function addClick(blogItem) {
     var dataId = blogItem.getAttribute('data-id');
     blogItem.addEventListener('click', function(event) {
        window.Screens.postMessage("blog-item", dataId);
     });
}

function addSeparator(item) {
    var div = document.createElement('div');
    div.className = "separator";
    item.appendChild(div);
}

modifyItem();