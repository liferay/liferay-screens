cordova.define("phonegap-plugin-battery-status.battery", function(require, exports, module) {
/*
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 *
*/
/* globals Promise */
/**
 * This class contains information about the current battery status.
 * @constructor
 */
var exec = cordova.require('cordova/exec');

var BatteryManager = function() {
  this.charging = true;
  this.chargingTime = 0;
  this.dischargingTime = Number.POSITIVE_INFINITY;
  this.level = 1.0;
  this._handlers = {
    'chargingchange': [],
    'chargingtimechange': [],
    'dischargingtimechange': [],
    'levelchange': []
  };

  this.onchargingchange = function() {};
  this.onchargingtimechange = function() {};
  this.ondischargingtimechange = function() {};
  this.onlevelchange = function() {};

  var that = this;
  var success = function(batteryInfo) {
    if (that.charging !== batteryInfo.charging) {
      that.charging = batteryInfo.charging;
      that.onchargingchange();
      that.emit('chargingchange');
    }
    if (that.chargingTime !== batteryInfo.chargingTime) {
      that.chargingTime = batteryInfo.chargingTime;
      that.onchargingtimechange();
      that.emit('chargingtimechange');
    }
    if (that.dischargingTime !== batteryInfo.dischargingTime) {
      that.dischargingTime = batteryInfo.dischargingTime;
      that.ondischargingtimechange();
      that.emit('dischargingtimechange');
    }
    if (that.level !== batteryInfo.level) {
      that.level = batteryInfo.level;
      that.onlevelchange();
      that.emit('levelchange');
    }
  };

  exec(success, null, "Battery","getBatteryStatus", []);
};

BatteryManager.prototype.addEventListener = function(eventName, callback) {
  if (!this._handlers.hasOwnProperty(eventName)) {
    this._handlers[eventName] = [];
  }
  this._handlers[eventName].push(callback);
};

BatteryManager.prototype.removeEventListener = function(eventName, handle) {
  if (this._handlers.hasOwnProperty(eventName)) {
    var handleIndex = this._handlers[eventName].indexOf(handle);
    if (handleIndex >= 0) {
      this._handlers[eventName].splice(handleIndex, 1);
    }
  }
};

BatteryManager.prototype.emit = function() {
  var args = Array.prototype.slice.call(arguments);
  var eventName = args.shift();

  if (!this._handlers.hasOwnProperty(eventName)) {
    return false;
  }

  for (var i = 0, length = this._handlers[eventName].length; i < length; i++) {
    var callback = this._handlers[eventName][i];
    if (typeof callback === 'function') {
      callback.apply(undefined,args);
    } else {
      console.log('event handler: ' + eventName + ' must be a function');
    }
  }
  return true;
};

var getBattery = function() {
  return new Promise(function(resolve, reject) {
    var manager = new BatteryManager();
    resolve(manager);
  });
};

module.exports = getBattery;

});
