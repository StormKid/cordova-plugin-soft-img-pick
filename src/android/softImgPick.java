package cordova.plugin.soft.img.pick;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * This class echoes a string called from JavaScript.
 */
public class softImgPick extends CordovaPlugin {
    private static final String ACTION_REQUEST_READ_PERMISSION = "requestReadPermission";
    private static final String ACTION_SHOW_LIST = "showList"
    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if (action.equals(ACTION_REQUEST_READ_PERMISSION)) {
            requestPermissions();
            return true;
        }else if(action.equals(ACTION_SHOW_LIST)){
            

            return true;
        }
        return false;
    }


    @SuppressLint("InlinedApi")
    private void requestReadPermission() {
        if (!hasReadPermission()) {
            ActivityCompat.requestPermissions(
                this.cordova.getActivity(),
                new String[] {Manifest.permission.READ_EXTERNAL_STORAGE},
                PERMISSION_REQUEST_CODE);
        }
        callbackContext.success();
    }

    private boolean hasReadPermission() {
        return Build.VERSION.SDK_INT < 23 ||
            PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(this.cordova.getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE);
    }
    
}
