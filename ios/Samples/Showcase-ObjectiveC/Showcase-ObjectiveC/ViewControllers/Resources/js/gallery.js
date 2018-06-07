const addClickToCards = () => {
	var cards = document.getElementsByClassName('card');
	
	for (let i = 0; i < cards.length; i++) {
		cards[i].addEventListener('click', function (event) {
			let card = cards[i];
			let imgSrc = card.querySelector('img').src;
			event.stopPropagation();
			window.Screens.postMessage('gallery', imgSrc.slice(7));
								  
		})
	}
}

addClickToCards();
