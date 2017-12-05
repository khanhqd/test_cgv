package com.interact;

import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import android.net.Uri;

/**
 * Created by huutq on 6/19/17.
 */

public class InteractModules extends ReactContextBaseJavaModule {
    private ReactApplicationContext reactContext;

    public InteractModules(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
    }

    @Override
    public String getName() {
        return "Interact";
    }

    @ReactMethod
    public void openApp(String packageName, final Promise promise){
        Log.e("InteractModule","Data :"+ packageName);
        try{
            Intent LaunchIntent = reactContext.getPackageManager().getLaunchIntentForPackage(packageName);
            reactContext.startActivity( LaunchIntent );
            promise.resolve("open app success");
        } catch (Exception e) {
            promise.reject("error","can not open app");
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            i.setData(Uri.parse("https://play.google.com/store/apps/details?id="+packageName));
            try {
                reactContext.startActivity(i);
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
    }

    @ReactMethod
    public void checkSimulator(final Promise promise){
        //check có bluetooth k
        //baseband option
    }

    @ReactMethod
    public void checkCHVer(final Promise promise){
        try {
            String v = reactContext.getPackageManager().getPackageInfo("com.google.android.gms", 0).versionName;
            promise.resolve(v);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

}
