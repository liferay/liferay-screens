cordova.define("com.helloarron.cordova.toast.AToast", function (require, exports, module) {

  /**
   Copyright 2015-2016, Arron http://helloarron.com
   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at
   http://www.apache.org/licenses/LICENSE-2.0
   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
   */

  var exec = require('cordova/exec');
  var platform = require('cordova/platform');

  var AToast = {
    toastMsg: "默认Toast样", toastLength: "SHORT", toastGravity: ["BOTTOM", 0, 0],

    toast: function (msg, ln, gv, img, callback) {
      AToast.toastLength = this.checkLength(ln);

      if (arguments.length == 0) {
        cordova.exec(callback, null, "AToast", "toast", [AToast.toastMsg, AToast.toastLength]);
      } else if (arguments.length == 1) {
        if (typeof arguments[0] == 'function') {
          cordova.exec(callback, null, "AToast", "toast", [AToast.toastMsg, AToast.toastLength]);
        } else if (typeof arguments[0] == 'string') {
          AToast.toastMsg = this.checkMsg(msg);
          cordova.exec(callback, null, "AToast", "toast", [AToast.toastMsg, AToast.toastLength]);
        } else {
          console.log('arguments error !');
        }
      } else if (arguments.length == 2) {
        if (typeof arguments[0] == 'string' && typeof arguments[1] == 'string') {
          AToast.toastMsg = this.checkMsg(msg);
          AToast.toastLength = this.checkLength(ln);
          cordova.exec(callback, null, "AToast", "toast", [AToast.toastMsg, AToast.toastLength]);
        } else {
          console.log('arguments error !');
        }
      } else if (arguments.length == 3) {
        if (typeof arguments[0] == 'string' && typeof arguments[1] == 'string' && typeof arguments[2] == 'object') {
          AToast.toastMsg = this.checkMsg(msg);
          AToast.toastLength = this.checkLength(ln);
          AToast.toastGravity = this.checkGravity(gv);
          cordova.exec(callback, null, "AToast", "toast", [AToast.toastMsg, AToast.toastLength, AToast.toastGravity]);
        } else {
          console.log('arguments error !');
        }
      } else {
        console.log('arguments error !');
      }
    },

    checkMsg: function (msg) {
      if (msg || msg.length > 0) {
        return msg + "";
      } else {
        return AToast.toastMsg;
      }
    },

    checkLength: function (ln) {
      if (ln == "LONG") {
        return "LONG";
      } else {
        return "SHORT";
      }
    },

    checkGravity: function (gv) {
      if (Object.prototype.toString.call(gv) === '[object Array]') {
        switch (gv[0]) {
          case "TOP":
            gv[0] = "TOP";
            break;
          case "CENTER":
            gv[0] = "CENTER";
            break;
          case "AXIS_CLIP":
            gv[0] = "AXIS_CLIP";
            break;
          case "AXIS_PULL_AFTER":
            gv[0] = "AXIS_PULL_AFTER";
            break;
          case "AXIS_PULL_BEFORE":
            gv[0] = "AXIS_PULL_BEFORE";
            break;
          case "AXIS_SPECIFIED":
            gv[0] = "AXIS_SPECIFIED";
            break;
          case "AXIS_X_SHIFT":
            gv[0] = "AXIS_X_SHIFT";
            break;
          case "AXIS_Y_SHIFT":
            gv[0] = "AXIS_Y_SHIFT";
            break;
          case "BOTTOM":
            gv[0] = "BOTTOM";
            break;
          case "CENTER_HORIZONTAL":
            gv[0] = "CENTER_HORIZONTAL";
            break;
          case "CENTER_VERTICAL":
            gv[0] = "CENTER_VERTICAL";
            break;
          case "CLIP_HORIZONTAL":
            gv[0] = "CLIP_HORIZONTAL";
            break;
          case "CLIP_VERTICAL":
            gv[0] = "CLIP_VERTICAL";
            break;
          case "DISPLAY_CLIP_HORIZONTAL":
            gv[0] = "DISPLAY_CLIP_HORIZONTAL";
            break;
          case "DISPLAY_CLIP_VERTICAL":
            gv[0] = "DISPLAY_CLIP_VERTICAL";
            break;
          case "FILL":
            gv[0] = "FILL";
            break;
          case "FILL_HORIZONTAL":
            gv[0] = "FILL_HORIZONTAL";
            break;
          case "FILL_VERTICAL":
            gv[0] = "FILL_VERTICAL";
            break;
          case "HORIZONTAL_GRAVITY_MASK":
            gv[0] = "HORIZONTAL_GRAVITY_MASK";
            break;
          case "LEFT":
            gv[0] = "LEFT";
            break;
          case "NO_GRAVITY":
            gv[0] = "NO_GRAVITY";
            break;
          case "RIGHT":
            gv[0] = "RIGHT";
            break;
          case "VERTICAL_GRAVITY_MASK":
            gv[0] = "VERTICAL_GRAVITY_MASK";
            break;
          default:
            gv[0] = "BOTTOM";
        }
        if (isNaN(gv[1])) {
          gv[1] = 0;
        } else if (isNaN(gv[0])) {
          gv[2] = 0;
        }
        return gv;
      } else {
        console.log("Please Set Gravity as ['CENTER' or 'TOP', 0, 0] ");
      }
    },
  }

  module.exports = AToast;

});
