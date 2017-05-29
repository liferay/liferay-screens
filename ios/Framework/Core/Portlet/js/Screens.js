window.Screens = {
	screensScripts_: [],
	addScreensScript: (screensScript) => {
		screensScript();
		window.Screens.screensScripts_.push(screensScript);
	},

	reloadScripts: () => {
		window.Screens.screensScripts_.forEach(fn => fn());
	}
}

// Attach a listener to the SPA application to add the Screens scripts
// on SPA navigation
window.Liferay.on('SPAReady', () => {
	Liferay.SPA.app.on('endNavigate', () => {
		window.webkit.messageHandlers.gallery.postMessage("test")
		window.Screens.reloadScripts();
	})
})
