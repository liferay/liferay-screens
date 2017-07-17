const addClickToCards = () => {
	var cards = document.getElementsByClassName('card');

	for (let i = 0; i < cards.length; i++) {
		cards[i].addEventListener('click', function(event) {
			let card = cards[i];
			let imgSrc = card.querySelector('img').src;
			event.stopPropagation();


								  if (isShow) {
								  isShow = false
								  NetworkActivityIndicator.hide();
								  }
								  else {
								  isShow = true;
								  NetworkActivityIndicator.show();
								  }

		window.Screens.postMessage("gallery", imgSrc.slice(7));

		})
	}
}

var isShow = false;

addClickToCards();

window.Screens.addScreensScript(addClickToCards);
