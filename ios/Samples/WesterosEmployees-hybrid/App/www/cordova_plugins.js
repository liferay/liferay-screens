cordova.define('cordova/plugin_list', function(require, exports, module) {
module.exports = [
    {
        "id": "call-number.CallNumber",
        "file": "plugins/call-number/www/CallNumber.js",
        "pluginId": "call-number",
        "clobbers": [
            "call"
        ]
    },
    {
        "id": "cordova-plugin-device.device",
        "file": "plugins/cordova-plugin-device/www/device.js",
        "pluginId": "cordova-plugin-device",
        "clobbers": [
            "device"
        ]
    }
];
module.exports.metadata = 
// TOP OF METADATA
{
    "cordova-plugin-whitelist": "1.3.2",
    "call-number": "0.0.2",
    "cordova-plugin-device": "1.1.6"
};
// BOTTOM OF METADATA
});