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
		if (navigator.userAgent.indexOf('Android') === -1) {
			return false;
		}
		return true;
	},

	postMessage: function(namespace, message) {
		if (this.isAndroid()) {
			android.postMessage(namespace, message);
		}
		else {
			window.webkit.messageHandlers[namespace].postMessage(message);
		}
	}
}

window.Screens = Object.create(screens);

window.Liferay.on('endNavigate', () => {
	window.Screens.reloadScripts();
});


// Attach a proxy to the Liferay object so we can inject our custom session 
window.Liferay = new Proxy(window.Liferay, {
	set: function(target, name, value) {
		if (name === "Session") {
			target[name] = new Liferay.SessionBase({ autoExtend: true, sessionLength: 30 * 60, warningLength: 60 });
		}
		else {
			target[name] = value;
	    }
	}
});
