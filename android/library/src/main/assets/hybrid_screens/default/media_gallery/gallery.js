function addClickToCards() {
	var cards = document.getElementsByClassName('card');
    var allImgSrc = "";
	for (let i = 0; i < cards.length; i++) {
		allImgSrc += cards[i].querySelector('img').src + ",";;
    	cards[i].addEventListener('click', function(event) {
    	allImgSrc += i;
    	event.stopPropagation();
    	android.postMessage("gallery", allImgSrc);
    })}
}

addClickToCards();