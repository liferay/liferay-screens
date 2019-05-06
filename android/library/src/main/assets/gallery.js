function addClickToCards() {
  var cards = document.getElementsByClassName('card');
  var allImgSrc = '';
  for (var i = 0; i < cards.length; i++) {
    allImgSrc += cards[i].querySelector('img').src + ',';
    addEvent(cards[i], i, allImgSrc);
  }
}

function addEvent(image, index, allImgSrc) {
  image.addEventListener('click', function (event) {
    var allAndCurrent = allImgSrc + index;
    event.stopPropagation();
    window.Screens.postMessage('gallery', allAndCurrent);
  })
}

addClickToCards();