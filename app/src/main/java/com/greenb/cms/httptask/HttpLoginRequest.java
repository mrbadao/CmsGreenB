package com.greenb.cms.httptask;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;

import com.greenb.cms.api.CoreApi;
import com.greenb.cms.httpinterface.LoginInterface;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by HieuNguyen on 7/30/2015.
 */
public class HttpLoginRequest extends AsyncTask<String[], Void, JSONObject> {
    public LoginInterface delegate;
    private Context context;

    public HttpLoginRequest(Context context, LoginInterface delegate) {
        this.context = context;
        this.delegate = delegate;
    }

    @Override
    protected void onPreExecute() {
    }

    @Override
    protected JSONObject doInBackground(String[]... params) {
        return CoreApi.Login(params[0][0], params[0][1]);
    }

    protected void onPostExecute(JSONObject jsonObject) {
        if (jsonObject != null) {
            if (!jsonObject.isNull("success") && jsonObject.optBoolean("success")) {
                String uId = null;
                String token = null;
                try {
                    uId = jsonObject.getString("uid");
                    token = jsonObject.getString("token");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (uId != null && token != null) {
                    SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this.context);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putString("com.greenb.cms.user.uid", uId);
                    editor.putString("com.greenb.cms.user.token", token);
                    editor.commit();
                    delegate.onUserLoginSuccess();
                }
            }
        }
    }
}