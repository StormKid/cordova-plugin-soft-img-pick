package cordova.plugin.soft.img.pick;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;

/**
 * This class echoes a string called from JavaScript.
 */
public class softImgPick extends CordovaPlugin {
    private static final String ACTION_REQUEST_READ_PERMISSION = "requestReadPermission";
    private static final String ACTION_SHOW_LIST = "showList";
    private static final int PERMISSION_REQUEST_CODE = 10086;
    private CallbackContext callbackContext;

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        this.callbackContext = callbackContext;
        if (action.equals(ACTION_REQUEST_READ_PERMISSION)) {
            requestReadPermission();
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
