var screens = {
  screensScripts_: [],
  addScreensScript: function(screensScript) {
    this.screensScripts_.push(screensScript);
  },

  reloadScripts: function() {
    this.screensScripts_.forEach(fn => fn());
  },

  isAndroid: function() {
    if (navigator.userAgent.indexOf("Android") === -1) {
      return false;
    }
    return true;
  },

  postMessage: function(namespace, message) {
    if (this.isAndroid()) {
      android.postMessage(namespace, message);
    } else {
      if (window.cordova) {
        document.addEventListener("deviceready", () => {
          cordova.exec(null, null, "ScreensBridgePlugin", "postMessage", [
            namespace,
            message
          ]);
        });
      } else if (window.webkit) {
        window.webkit.messageHandlers.screensDefault.postMessage([
          namespace,
          message
        ]);
      }
    }
  },

  listPortlets: function() {
    let portlets = window.Liferay.Portlet.list;

    let parsedPortlets = portlets
      .map(portlet => {
        let portletSplitted = portlet.split("_");
        let length = portletSplitted.length;
        if (length >= 3 && portletSplitted[length - 2] === "INSTANCE") {
          return portletSplitted.slice(0, length - 2).join("_");
        } else {
          return portlet;
        }
      })
      .filter((x, idx, arr) => arr.indexOf(x) === idx)
      .join(",");

    this.postMessage("screensinternal.listportlets", parsedPortlets);
  }
};

window.Screens = Object.create(screens);

if (window.Liferay) {
  window.Liferay.on("endNavigate", () => {
    window.Screens.reloadScripts();
  });

  // Attach a proxy to the Liferay object so we can inject our custom session
  window.Liferay = new Proxy(window.Liferay, {
    set: function(target, name, value) {
      if (name === "Session") {
        target[name] = new Liferay.SessionBase({
          autoExtend: true,
          sessionLength: 5 * 60,
          warningLength: 60
        });
      }

      target[name] = value;
    }
  });
}
