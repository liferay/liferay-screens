cordova.define('cordova/plugin_list', function (require, exports, module) {
  module.exports = [{
    "id": "com.helloarron.cordova.toast.AToast",
    "file": "plugins/com.helloarron.cordova.toast/www/AToast.js",
    "pluginId": "com.helloarron.cordova.toast",
    "clobbers": ["AToast"],
    "merges": ["AToast"]
  }];
  module.exports.metadata = // TOP OF METADATA
      {
        "cordova-plugin-whitelist": "1.3.2",
      };
  // BOTTOM OF METADATA
});