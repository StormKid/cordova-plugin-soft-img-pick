package com.soft.img.pick;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * This class echoes a string called from JavaScript.
 */
public class softImgPick extends CordovaPlugin {
    private static final String ACTION_REQUEST_READ_PERMISSION = "requestReadPermission";
    private static final String ACTION_SHOW_LIST = "showList";
    private static final int PERMISSION_REQUEST_CODE = 10086;
    private static  final  int RESULT_CODE = 10010;
    private CallbackContext callbackContext;
    private String ok_text;
    private String cancel_text;
    private String check_img_res;
    private int manager_title_text_size;
    private int limit_num;
    private boolean post_imgs;
    private String path_url;
    private String file_key;
    private JSONObject photo_params;
    private String banner_color;


    private final String OK_TEXT="ok_text";
    private final String CANCEL_TEXT = "cancel_text";
    private final String CHECK_IMG_RES = "check_img_res";
    private final String MANAGER_TITLE_TEXT_SIZE = "manager_title_text_size";
    private final String LIMIT_NUM="limit_num";
    private final  String POST_IMGS = "post_imgs";
    private final  String PATH_URL = "path_url";
    private final  String FILE_KEY="file_key";
    private final String BANNER_COLOR = "banner_color";
    private final String PHOTO_PARAMS = "photo_params";

    private  final  static  String ERR = "请选择您所需要的图片";


    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        this.callbackContext = callbackContext;
        if (action.equals(ACTION_REQUEST_READ_PERMISSION)) {
            requestReadPermission();
            return true;
        }else if(action.equals(ACTION_SHOW_LIST)){
            JSONObject object = args.getJSONObject(0);
            final Intent intent = new Intent(cordova.getActivity(), com.soft.img.pick.ImagePickActivity.class);
            if (object.has(OK_TEXT)) ok_text = object.getString(OK_TEXT);
            if (object.has(CANCEL_TEXT)) cancel_text = object.getString(CANCEL_TEXT);
            if (object.has(CHECK_IMG_RES)) check_img_res = object.getString(CHECK_IMG_RES);
            if (object.has(MANAGER_TITLE_TEXT_SIZE)) manager_title_text_size = object.getInt(MANAGER_TITLE_TEXT_SIZE);
            if (object.has(LIMIT_NUM))limit_num =object.getInt(LIMIT_NUM);
            if (object.has(POST_IMGS)) post_imgs=object.getBoolean(POST_IMGS);
            if (object.has(PATH_URL)) path_url= object.getString(PATH_URL);
            if (object.has(FILE_KEY)) file_key=object.getString(FILE_KEY);
            if (object.has(PHOTO_PARAMS)) photo_params = object.getJSONObject(PHOTO_PARAMS);
            if (object.has(BANNER_COLOR)) banner_color = object.getString(BANNER_COLOR);
            Bundle bundle = toBundle(photo_params);
            intent.putExtra(OK_TEXT,ok_text);
            intent.putExtra(CANCEL_TEXT,cancel_text);
            intent.putExtra(CHECK_IMG_RES,check_img_res);
            intent.putExtra(MANAGER_TITLE_TEXT_SIZE,manager_title_text_size);
            intent.putExtra(LIMIT_NUM,limit_num);
            intent.putExtra(POST_IMGS,post_imgs);
            intent.putExtra(PATH_URL,path_url);
            intent.putExtra(FILE_KEY,file_key);
            intent.putExtra(BANNER_COLOR,banner_color);
            intent.putExtras(bundle);

            if (hasReadPermission()) cordova.startActivityForResult(this,intent,RESULT_CODE);
            else requestReadPermission();
            return true;
        }
        return false;
    }

    /**
     * 只允许Stirng 参数
     * @param object
     */
    private Bundle toBundle(JSONObject object) throws JSONException {
        if (null==object) return null;
        Iterator<String> keys = object.keys();
        Bundle bundle = new Bundle();
        while (keys.hasNext()){
            String key = keys.next();
            String value = object.getString(key);
            bundle.putString(key,value);
        }
        return bundle;
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (resultCode == Activity.RESULT_OK && intent != null) { //获取选择相册的path
            ArrayList<String> fileNames = intent.getStringArrayListExtra("path_list");
            String rep = intent.getStringExtra("result_rep");
            if (null!=fileNames&&fileNames.size()>0){
            JSONArray res = new JSONArray(fileNames);
            callbackContext.success(res);
            }else if (!TextUtils.isEmpty(rep)){ //获取请求回来返回的数据
                callbackContext.success(rep);
            }

        } else if (resultCode == Activity.RESULT_CANCELED && intent != null) {
            callbackContext.error(ERR);

        } else if (resultCode == Activity.RESULT_CANCELED) {

            callbackContext.success(ERR);

        } else {
            callbackContext.error(ERR);
        }
    }

    /**
     * 保证请求状态发生异常时能够返回此页面
     * @param state             Bundle containing the state of the plugin
     * @param callbackContext   Replacement Context to return the plugin result to
     */
    @Override
    public void onRestoreStateForActivityResult(Bundle state, CallbackContext callbackContext) {
       this.callbackContext=callbackContext
    }
}
