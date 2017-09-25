cordova.define("cordova-plugin-networkactivityindicator.NetworkActivityIndicator", function(require, exports, module) {
var NetworkActivityIndicator = {

  show: function(success, error) {
    cordova.exec(
      success,
      error,
      "NetworkActivityIndicator",
      "showNetworkActivityIndicator",
      []
    );
  },

  hide: function(success, error) {
    cordova.exec(
      success,
      error,
      "NetworkActivityIndicator",
      "hideNetworkActivityIndicator",
      []
    );
  }

};

module.exports = NetworkActivityIndicator;
});
