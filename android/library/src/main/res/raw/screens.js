window.addEventListener('error', function (ev) {
  if (window.Screens) {
    window.Screens.postMessage('screensInternal.error', 'Error in file ' + window.currentFile + ': ' + ev.message);
  }
});

window.currentFile = "";

var screens = {
  screensScripts_: [], addScreensScript: function (screensScript) {
    this.screensScripts_.push(screensScript);
  },

  reloadScripts: function () {
    this.screensScripts_.forEach(function (scripts) {
      scripts();
    });
  },

  postMessage: function (namespace, message) {
    android.postMessage(namespace, message);
  }
};

window.Screens = Object.create(screens);

window.Liferay.on('endNavigate', function () {
  window.Screens.reloadScripts();
});