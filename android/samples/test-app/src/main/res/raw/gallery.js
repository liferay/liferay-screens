var cards = document.getElementsByClassName('card');
var allImgSrc = [];

for (let i = 0; i < cards.length; i++) {
    allImgSrc[i] = cards[i].querySelector('img').src;

	cards[i].addEventListener('click', function(event) {
		event.stopPropagation();

		android.setAllImgSrc(allImgSrc);
		android.showItem(i);
	})
};