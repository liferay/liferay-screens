window.Screens = {
	screensScripts_: [],
	addScreensScript: (screensScript) => {
		window.Screens.screensScripts_.push(screensScript);
	},

	reloadScripts: () => {
		window.Screens.screensScripts_.forEach(fn => fn());
	}
};


Liferay.SPA.app.on('endNavigate', () => {
	window.Screens.reloadScripts();
})
