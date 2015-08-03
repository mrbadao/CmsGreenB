package com.greenb.cms.httptask;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;

import com.greenb.cms.R;
import com.greenb.cms.api.CoreApi;
import com.greenb.cms.httpinterface.LoginInterface;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by HieuNguyen on 7/30/2015.
 */
public class HttpLoginRequest extends AsyncTask<Void, Void, JSONObject> {
    public LoginInterface delegate;
    private Context context;
    private final String mEmail;
    private final String mPassword;

    public HttpLoginRequest(Context context, LoginInterface delegate, String email, String password) {
        this.context = context;
        this.delegate = delegate;
        mEmail = email;
        mPassword = password;
    }

    @Override
    protected JSONObject doInBackground(Void... params) {
        return CoreApi.Login(this.mEmail, this.mPassword);
    }

    @Override
    protected void onPostExecute(JSONObject jsonObject) {
        if (jsonObject != null && !jsonObject.isNull("success")) {
            try {
                if (jsonObject.optBoolean("success")) {
                    jsonObject = jsonObject.getJSONObject("data");
                    String uId = jsonObject.getString("uid");
                    String token = jsonObject.getString("token");

                    if (uId != null && token != null) {
                        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this.context);
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putString("com.greenb.cms.user.uid", uId);
                        editor.putString("com.greenb.cms.user.token", token);
                        editor.commit();
                        delegate.onUserLoginSuccess();
                        return;
                    }
                } else {
                    if (!jsonObject.isNull("error") || !jsonObject.isNull("data")) {
                        delegate.onUserLoginFaild(this.context.getString(R.string.error_incorrect_password));
                        return;
                    }
                }

            } catch (JSONException e) {
                delegate.onUserLoginFaild(this.context.getString(R.string.error_incorrect_password));
                return;
            }
        } else delegate.onUserLoginFaild("Faild to connect server.");
    }
}