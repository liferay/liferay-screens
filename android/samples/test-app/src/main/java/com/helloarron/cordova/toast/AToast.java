package com.helloarron.cordova.toast;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AToast extends CordovaPlugin {
    public Context myContext = null;
    private String toastMsg = "";
    private String toastLength = "";
    private JSONArray toastGravity = null;

    @Override
    public void initialize(CordovaInterface cordova, CordovaWebView webView) {

        super.initialize(cordova, webView);
        myContext = this.webView.getContext();
    }

    @Override
    public boolean execute(String action, JSONArray data, CallbackContext callbackContext) throws JSONException {
        if (action.equals("toast")) {
            JSONObject obj = new JSONObject();
            try {
                toastMsg = data.getString(0);
                toastLength = data.get(1).toString();
                if (data.length() == 3 && !data.get(2).equals("null")) {
                    toastGravity = (JSONArray) data.get(2);
                    this.showToast(toastMsg, toastLength, toastGravity);
                } else {
                    this.showToast(toastMsg, toastLength);
                }

                obj.put("error", 0);
                obj.put("msg", "Toast success");
            } catch (JSONException e) {
                e.printStackTrace();
                callbackContext.error("JSON Exception");
            }
            callbackContext.success(obj);
        }
        return true;
    }

    private void showToast(String msg, String ln) {
        if (ln.equals("LONG")) {
            Toast.makeText(cordova.getActivity(), msg, Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(cordova.getActivity(), msg, Toast.LENGTH_SHORT).show();
        }
    }

    private void showToast(String msg, String ln, JSONArray gv) {
        Toast toast = null;
        if (ln.equals("LONG")) {
            toast = Toast.makeText(cordova.getActivity(), msg, Toast.LENGTH_LONG);
        } else {
            Toast.makeText(cordova.getActivity(), msg, Toast.LENGTH_SHORT).show();
        }
        try {
            switch (gv.get(0).toString()) {
                case "CENTER":
                    toast.setGravity(Gravity.VERTICAL_GRAVITY_MASK, (int) gv.get(1), (int) gv.get(2));
                    break;
                case "TOP":
                    toast.setGravity(Gravity.TOP, (int) gv.get(1), (int) gv.get(2));
                    break;
                case "AXIS_CLIP":
                    toast.setGravity(Gravity.AXIS_CLIP, (int) gv.get(1), (int) gv.get(2));
                    break;
                case "AXIS_PULL_AFTER":
                    toast.setGravity(Gravity.AXIS_PULL_AFTER, (int) gv.get(1), (int) gv.get(2));
                    break;
                case "AXIS_PULL_BEFORE":
                    toast.setGravity(Gravity.AXIS_PULL_BEFORE, (int) gv.get(1), (int) gv.get(2));
                    break;
                case "AXIS_SPECIFIED":
                    toast.setGravity(Gravity.AXIS_SPECIFIED, (int) gv.get(1), (int) gv.get(2));
                    break;
                case "AXIS_X_SHIFT":
                    toast.setGravity(Gravity.AXIS_X_SHIFT, (int) gv.get(1), (int) gv.get(2));
                    break;
                case "AXIS_Y_SHIFT":
                    toast.setGravity(Gravity.AXIS_Y_SHIFT, (int) gv.get(1), (int) gv.get(2));
                    break;
                case "BOTTOM":
                    toast.setGravity(Gravity.BOTTOM, (int) gv.get(1), (int) gv.get(2));
                    break;
                case "CENTER_HORIZONTAL":
                    toast.setGravity(Gravity.CENTER_HORIZONTAL, (int) gv.get(1), (int) gv.get(2));
                    break;
                case "CENTER_VERTICAL":
                    toast.setGravity(Gravity.CENTER_VERTICAL, (int) gv.get(1), (int) gv.get(2));
                    break;
                case "CLIP_HORIZONTAL":
                    toast.setGravity(Gravity.CLIP_HORIZONTAL, (int) gv.get(1), (int) gv.get(2));
                    break;
                case "CLIP_VERTICAL":
                    toast.setGravity(Gravity.CLIP_VERTICAL, (int) gv.get(1), (int) gv.get(2));
                    break;
                case "DISPLAY_CLIP_HORIZONTAL":
                    toast.setGravity(Gravity.DISPLAY_CLIP_HORIZONTAL, (int) gv.get(1), (int) gv.get(2));
                    break;
                case "DISPLAY_CLIP_VERTICAL":
                    toast.setGravity(Gravity.DISPLAY_CLIP_VERTICAL, (int) gv.get(1), (int) gv.get(2));
                    break;
                case "FILL":
                    toast.setGravity(Gravity.FILL, (int) gv.get(1), (int) gv.get(2));
                    break;
                case "FILL_HORIZONTAL":
                    toast.setGravity(Gravity.FILL_HORIZONTAL, (int) gv.get(1), (int) gv.get(2));
                    break;
                case "FILL_VERTICAL":
                    toast.setGravity(Gravity.FILL_VERTICAL, (int) gv.get(1), (int) gv.get(2));
                    break;
                case "HORIZONTAL_GRAVITY_MASK":
                    toast.setGravity(Gravity.HORIZONTAL_GRAVITY_MASK, (int) gv.get(1), (int) gv.get(2));
                    break;
                case "LEFT":
                    toast.setGravity(Gravity.LEFT, (int) gv.get(1), (int) gv.get(2));
                    break;
                case "NO_GRAVITY":
                    toast.setGravity(Gravity.NO_GRAVITY, (int) gv.get(1), (int) gv.get(2));
                    break;
                case "RIGHT":
                    toast.setGravity(Gravity.RIGHT, (int) gv.get(1), (int) gv.get(2));
                    break;
                case "VERTICAL_GRAVITY_MASK":
                    toast.setGravity(Gravity.VERTICAL_GRAVITY_MASK, (int) gv.get(1), (int) gv.get(2));
                    break;
                default:
                    toast.setGravity(Gravity.BOTTOM, (int) gv.get(1), (int) gv.get(2));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        toast.show();
    }
}
