package com.logincenter;

import com.facebook.react.ReactActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import com.logincenter.LoginCenterModules;

import static com.logincenter.LoginCenterModules.CLOSE_MAIN_ACTION;
import static com.logincenter.LoginCenterModules.RECEIVE_ACTION;
import static com.logincenter.LoginCenterModules.REQUEST_KEY;

import org.json.JSONObject;
import org.json.JSONException;


public class LoginActivity extends ReactActivity {


    private static final String INTENT_ACTION = "LOGIN_CENTER";
    private ModuleListener moduleListener;
    private ModuleReceiver moduleReceiver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if(getIntent() != null && getIntent().hasExtra("clientId")){

            String clientId = getIntent().getStringExtra("clientId");
            String phoneNumber = "0";
            if(getIntent().hasExtra("phoneNumber")){
                phoneNumber = getIntent().getStringExtra("phoneNumber");
            }
            
            JSONObject data = new JSONObject();
            try {
                data.put("client_id",clientId);
                data.put("phone_number",phoneNumber);
                LoginCenterModules.dataThirdparty = data.toString();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            
            Log.i("LoginCenterActivity","Got request " + data.toString());
            

        }else{
            Log.i("LoginCenterActivity","Has no request key passed along with intent");
        }
        super.onCreate(savedInstanceState);
        IntentFilter intentFilter = new IntentFilter(RECEIVE_ACTION);
        moduleListener = new ModuleListener() {
            @Override
            public void onReceivedData(String receivedData) {
                sendValue(receivedData);
            }
        };
        moduleReceiver = new ModuleReceiver(moduleListener);
        LocalBroadcastManager.getInstance(this).registerReceiver(moduleReceiver,intentFilter);
        LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(CLOSE_MAIN_ACTION));
    }

    /**
     * Returns the name of the main component registered from JavaScript.
     * This is used to schedule rendering of the component.
     */
    @Override
    protected String getMainComponentName() {
        return "LoginCenter";
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onNewIntent (Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        if(intent != null && intent.hasExtra("requestKey")){
            REQUEST_KEY = intent.getLongExtra("requestKey",0);
            startActivity(intent);
            finish();

        }else{
            Log.i("LoginCenterActivity","Has no request key passed along with intent");
        }
    }

    public void sendValue(String data){
        Intent result = new Intent(INTENT_ACTION);
        result.putExtra("returnedValue",data);
        result.putExtra("requestKey",REQUEST_KEY);
        sendBroadcast(result);
        finish();
    }

    @Override
    protected void onDestroy() {
        try{
            LocalBroadcastManager.getInstance(this).unregisterReceiver(moduleReceiver);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        super.onDestroy();
    }

    interface ModuleListener{
        void onReceivedData(String receivedData);
    }

    class ModuleReceiver extends BroadcastReceiver {
        ModuleListener moduleListener;

        public ModuleReceiver(ModuleListener moduleListener){
            this.moduleListener = moduleListener;
        }
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent!= null && intent.getAction() != null && intent.getAction().equals(RECEIVE_ACTION) && intent.hasExtra(LoginCenterModules.RETURN_DATA_KEY)){
                if(moduleListener != null){
                    moduleListener.onReceivedData(intent.getStringExtra(LoginCenterModules.RETURN_DATA_KEY));
                }
            }
        }
    }
}
