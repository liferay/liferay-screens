function addClickToCards() {
	var cards = document.getElementsByClassName('card');
	var allImgSrc = '';
	for (var i = 0; i < cards.length; i++) {
		var currentImage = cards[i];
		allImgSrc += currentImage.querySelector('img').src + ",";
		(function (i) {
			currentImage.addEventListener('click', function (event) {
				var allAndCurrent = allImgSrc + i;
				event.stopPropagation();
				android.postMessage('gallery', allAndCurrent);
			});
		})(i)
	}
}

addClickToCards();