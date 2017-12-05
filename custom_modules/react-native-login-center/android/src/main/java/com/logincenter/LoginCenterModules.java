package com.logincenter;

import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

/**
 * Created by huutq on 6/19/17.
 */

public class LoginCenterModules extends ReactContextBaseJavaModule {
    
    public static final String RECEIVE_ACTION = "RECEIVER_ACTION";
    public static final String CLOSE_MAIN_ACTION = "CLOSE_MAIN_ACTION";
    public static final String RETURN_DATA_KEY = "return_data";
    public static String dataThirdparty="";
    public static long REQUEST_KEY = 0;
    public static String apiKey,phoneNumber;


    private ReactApplicationContext reactContext;


    public LoginCenterModules(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
    }

    @Override
    public String getName() {
        return "LoginCenter";
    }

    @ReactMethod
    public  void passDataResult(String data){
        Log.e("PassData","ImHere" + data);
        Intent dataReturn = new Intent(RECEIVE_ACTION);
        dataReturn.putExtra(RETURN_DATA_KEY,data);
        reactContext.sendBroadcast(dataReturn);
    }

    @ReactMethod
    public void getDataThirdparty(final Promise promise){
        Log.e("CenterModule","Data :"+ dataThirdparty);
        promise.resolve(dataThirdparty);
    }

    @ReactMethod
    public void getStartIntent(final Promise promise){
        Log.e("getStartIntent","Data :"+REQUEST_KEY);
        promise.resolve(String.valueOf( REQUEST_KEY));
    }

}
