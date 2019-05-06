function modifyItem() {
  var allLinks = document.getElementsByTagName('a');
  var allImages = document.getElementsByTagName('img');

  for (var i = 0; i < allLinks.length; i++) {
    allLinks[i].removeAttribute('href');
  }

  for (var j = 0; j < allImages.length; j++) {
    if (allImages[j].id !== 'img-header') {
      imageToDiv(allImages[j]);
    }
  }
}

modifyItem();

function imageToDiv(img) {
  var parent = img.parentNode;
  var newSibling = buildImageWithDiv(img);
  insertAfter(parent, newSibling);
  deleteTag(parent);
}

function buildImageWithDiv(img) {
  img.className += ' img-blog';
  img.removeAttribute('height');
  img.removeAttribute('width');
  img.setAttribute('style', 'margin: 20px 0px;');

  var div = document.createElement('div');
  div.className += ' img-content-blog';
  div.appendChild(img);
  return div;
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
