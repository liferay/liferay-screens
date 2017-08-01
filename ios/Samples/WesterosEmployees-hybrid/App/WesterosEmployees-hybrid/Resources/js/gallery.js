function modifyItem() {
    
    var galleryItems = document.querySelectorAll('.item_gallery');
    
    for (var i = 0; i < galleryItems.length; i++) {
        addClick(galleryItems[i]);
    }
}

function addClick(galleryItems) {
    var dataId = galleryItems.getAttribute('data-id');
    galleryItems.addEventListener('click', function(event) {
                                  window.Screens.postMessage('gallery-item', dataId);
                                  });
}

modifyItem();
