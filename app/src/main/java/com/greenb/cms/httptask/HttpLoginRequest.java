package com.greenb.cms.httptask;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;

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
    protected void onPreExecute() {
    }

    @Override
    protected JSONObject doInBackground(Void... params) {
        return CoreApi.Login(this.mEmail, this.mPassword);
    }

    @Override
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

            if (!jsonObject.isNull("success") && !jsonObject.optBoolean("success")) {
                if (!jsonObject.isNull("error")) {
                    try {
                        String msgError = jsonObject.getJSONObject("error").getString("message");
                        delegate.onUserLoginFaild(msgError);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

                if (!jsonObject.isNull("data")) {
                    if (jsonObject.has("AuthError")) {
                        String msgError = context.getString(R.string.error_incorrect_password);
                        delegate.onUserLoginFaild(msgError);
                    }
                }

            }
        }

        delegate.onUserLoginFaild("Faild to connect server.");
    }
}