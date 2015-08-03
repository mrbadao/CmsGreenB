package com.greenb.cms.httptask;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;

import com.greenb.cms.api.CoreApi;
import com.greenb.cms.httpinterface.LoginInterface;

import org.json.JSONObject;

/**
 * Created by HieuNguyen on 7/30/2015.
 */
public class HttpTokenValidateRequest extends AsyncTask<Void, Void, JSONObject> {
    public LoginInterface delegate;
    private Context context;
    private final String mUid;
    private final String mToken;

    public HttpTokenValidateRequest(Context context, LoginInterface delegate, String uid, String token) {
        this.context = context;
        this.delegate = delegate;
        mUid = uid;
        mToken = token;
    }

    @Override
    protected JSONObject doInBackground(Void... params) {
        return CoreApi.TokenValidate(this.mUid, this.mToken);
    }

    @Override
    protected void onPostExecute(JSONObject jsonObject) {
        if (jsonObject == null) {
            delegate.onUserLoginFaild("Faild to connect server.");
            return;
        }

        if (jsonObject != null && jsonObject.optBoolean("success")) {
            delegate.onUserLoginSuccess();
            return;
        } else {
            SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this.context);
            SharedPreferences.Editor editor = sharedPref.edit();

            if(sharedPref.contains("com.greenb.cms.user.uid")){
                editor.remove("com.greenb.cms.user.uid");
            }

            if(sharedPref.contains("com.greenb.cms.user.token")){
                editor.remove("com.greenb.cms.user.token");
            }
            editor.commit();
            delegate.onUserLoginFaild("Token Expire.");
            return;
        }
    }
}