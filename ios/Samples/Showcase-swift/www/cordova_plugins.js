cordova.define('cordova/plugin_list', function(require, exports, module) {
module.exports = [
    {
        "id": "es6-promise-plugin.Promise",
        "file": "plugins/es6-promise-plugin/www/promise.js",
        "pluginId": "es6-promise-plugin",
        "runs": true
    },
    {
        "id": "phonegap-plugin-battery-status.battery",
        "file": "plugins/phonegap-plugin-battery-status/www/battery.js",
        "pluginId": "phonegap-plugin-battery-status",
        "clobbers": [
            "navigator.getBattery"
        ]
    },
    {
        "id": "cordova-plugin-networkactivityindicator.NetworkActivityIndicator",
        "file": "plugins/cordova-plugin-networkactivityindicator/www/NetworkActivityIndicator.js",
        "pluginId": "cordova-plugin-networkactivityindicator",
        "clobbers": [
            "NetworkActivityIndicator"
        ]
    }
];
module.exports.metadata = 
// TOP OF METADATA
{
    "cordova-plugin-whitelist": "1.3.2",
    "es6-promise-plugin": "4.1.0",
    "phonegap-plugin-battery-status": "1.0.0",
    "cordova-plugin-networkactivityindicator": "0.1.1",
    "cordova-plugin-remote-injection": "0.5.2"
};
// BOTTOM OF METADATA
});
