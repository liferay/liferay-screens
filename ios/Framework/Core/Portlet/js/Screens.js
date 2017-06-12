var screens = {
	screensScripts_: [],
	addScreensScript: function(screensScript) {
		screensScript();
		this.screensScripts_.push(screensScript);
	},

	reloadScripts: function() {
		this.screensScripts_.forEach(fn => fn());
	},

	isAndroid: function() {
		if (navigator.userAgent.indexOf('Android') !== -1) {
			return false;
		}
		return true;
	},

	postMessage: function(namespace, message) {
		if (this.isAndroid) {
			android.postMessage(namespace, message);
		}
		else {
			window.webkit.messageHandlers[namespace].postMessage(message);
		}
	}
}

window.Screens = Object.create(screens);

// Attach a listener to the SPA application to add the Screens scripts
// on SPA navigation
window.Liferay.on('endNavigate', () => window.Screens.reloadScripts());
