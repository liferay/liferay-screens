function modifyItem() {
    
    var allLinks = document.getElementsByTagName('a');
    var allImages = document.getElementsByTagName('img');
    
    for (var i = 0; i < allLinks.length; i++) {
        allLinks[i].removeAttribute('href');
    }
    
    for (var i = 0; i < allImages.length; i++) {
        if (allImages[i].id != 'img-header') {
            imageToDiv(allImages[i]);
        }
    }
}

modifyItem();

function imageToDiv(allImages) {
    var parent = allImages.parentNode;
    var newSibling = buildImageWithDiv(allImages);
    insertAfter(parent, newSibling);
    deleteTag(parent);
}

function buildImageWithDiv(allImages) {
    allImages.className += ' img-blog';
    allImages.removeAttribute('height');
    allImages.removeAttribute('width');
    allImages.setAttribute('style', 'margin: 20px 0px;');
    var newDiv = document.createElement('div');
    newDiv.className += ' img-content-blog';
    newDiv.appendChild(allImages);
    return newDiv;
}

function insertAfter(parent, newSibling) {
    if (parent.nextSibling) {
        parent.parentNode.insertBefore(newSibling, parent.nextSibling);
    } else {
        parent.parentNode.appendChild(newSibling);
    }
}

function deleteTag(tag) {
    tag.remove();
}
