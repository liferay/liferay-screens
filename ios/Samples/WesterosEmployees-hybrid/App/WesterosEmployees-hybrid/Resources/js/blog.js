function modifyItem() {
    
    var etqA = document.getElementsByTagName("a");
    var etqImg = document.getElementsByTagName("img");
    
    for(var i = 0; i < etqA.length; i++) {
        etqA[i].removeAttribute("href");
    }
    
    for(var i = 0; i < etqImg.length; i++){
        if(etqImg[i].id != "img-header"){
            imageToDiv(etqImg[i]);
        }
    }
}

modifyItem();

function cleanRatingText(rating){
    rating.innerHTML = rating.textContent.replace("Average (", "").replace(")", "");
}


function imageToDiv(etqImg){
    var parent = etqImg.parentNode;
    var newSibling = buildImageWithDiv(etqImg);
    insertAfter(parent, newSibling);
    deleteEtq(parent);
}

function buildImageWithDiv(etqImg){
    etqImg.className += " img-blog";
    etqImg.removeAttribute("height");
    etqImg.removeAttribute("width");
    etqImg.setAttribute("style", "margin: 20px 0px;");
    var newDiv = document.createElement("div");
    newDiv.className += " img-content-blog";
    newDiv.appendChild(etqImg);
    return newDiv;
}

function insertAfter(parent, newSibling){
    if(parent.nextSibling){
        parent.parentNode.insertBefore(newSibling,parent.nextSibling);
    } else {
        parent.parentNode.appendChild(newSibling);
    }
}

function deleteEtq(etq){
    etq.remove();
}
