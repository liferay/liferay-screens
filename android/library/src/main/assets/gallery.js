function addClickToCards() {
	var cards = document.getElementsByClassName('card');
    var allImgSrc = '';
	for (var i = 0; i < cards.length; i++) {
		allImgSrc += cards[i].querySelector('img').src + ',';
    	cards[i].addEventListener('click', function(event) {
            var allAndCurrent = allImgSrc + i;
            event.stopPropagation();
            window.Screens.postMessage('gallery', allAndCurrent);
    })}
}

addClickToCards();