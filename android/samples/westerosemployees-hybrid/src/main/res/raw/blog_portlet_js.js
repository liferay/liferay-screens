function modifyItem() {

    var allLinks = document.getElementsByTagName("a");
    var allImg = document.getElementsByTagName("img");

    for (var i = 0; i < allLinks.length; i++) {
        allLinks[i].removeAttribute("href");
    }

    for (var i = 0; i < allImg.length; i++){
        if (allImg[i].id != "img-header"){
        imageToDiv(allImg[i]);
        }
    }
}

modifyItem();

function imageToDiv(allImg){
    var parent = allImg.parentNode;
    var newSibling = buildImageWithDiv(allImg);
    insertAfter(parent, newSibling);
    deleteTag(parent);
}

function buildImageWithDiv(allImg){
    allImg.className += " img-blog";
    allImg.removeAttribute("height");
    allImg.removeAttribute("width");
    allImg.setAttribute("style", "margin: 20px 0px;");
    var newDiv = document.createElement("div");
    newDiv.className += " img-content-blog";
    newDiv.appendChild(allImg);
    return newDiv;
}

function insertAfter(parent, newSibling){
        if(parent.nextSibling){
            parent.parentNode.insertBefore(newSibling,parent.nextSibling);
        } else {
            parent.parentNode.appendChild(newSibling);
        }
}

function deleteTag(tag){
    tag.remove();
}