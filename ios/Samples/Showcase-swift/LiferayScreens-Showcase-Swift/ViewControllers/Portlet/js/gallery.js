const addClickToCards = () => {
	var cards = document.getElementsByClassName('card');

	for (let i = 0; i < cards.length; i++) {
		cards[i].addEventListener('click', function(event) {
			let card = cards[i];
			let imgSrc = card.querySelector('img').src;
			event.stopPropagation();
			window.webkit.messageHandlers.gallery.postMessage(imgSrc)
		})
	}
}

addClickToCards()

window.Liferay = new Proxy(window.Liferay || {}, {
	 set: function(target, name, value) {
		if (name === "SPA") {
			target[name] = new Proxy(value, {
				 set: function(target, name, value) {
					if (name === "app") {
						value.events_.endNavigate.push({ fn: () => addClickToCards()})
					}
					target[name] = value
				}
			})
		}
		else {
			target[name] = value
		}
	 }
})
