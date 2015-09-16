package com.greenb.cms.httptask;

import android.content.Context;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.greenb.cms.api.CoreApi;
import com.greenb.cms.httpinterface.AddCashierInterface;
import com.greenb.cms.models.Cashier;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by HieuNguyen on 7/30/2015.
 */
public class HttpEditCashierRequest extends AsyncTask<JSONObject, Void, JSONObject> {
    public AddCashierInterface delegate;
    private Context context;
    private final String mAuthorization;

    public HttpEditCashierRequest(Context context, String authorization, AddCashierInterface delegate) {
        this.context = context;
        this.delegate = delegate;
        mAuthorization = authorization;
    }

    @Override
    protected JSONObject doInBackground(JSONObject... params) {
        return CoreApi.EditCashier(params[0], this.mAuthorization);
    }

    @Override
    protected void onPostExecute(JSONObject jsonObject) {
        if (jsonObject != null) {
            try {
                boolean isSuccess = jsonObject.getBoolean("success");
                if (isSuccess) {
                    jsonObject = jsonObject.getJSONObject("data");
                    JSONObject jsonCashier = jsonObject.getJSONObject("cashier");

                    Gson gson = new Gson();
                    this.delegate.onCashierAddSuccessly(gson.fromJson(jsonCashier.toString(), Cashier.class));
                    return;
                } else {
                    JSONObject jsonError = jsonObject.getJSONObject("error");
                    HashMap<String, String> hashMapValidateError = new HashMap<String, String>();
                    switch (jsonError.getInt("code")) {
                        case 1001:
                            JSONObject jsonContent = jsonError.getJSONObject("content");

                            Iterator<String> iter = jsonContent.keys();
                            while (iter.hasNext()) {
                                String key = iter.next();
                                JSONArray errorArray = jsonContent.getJSONArray(key);
                                hashMapValidateError.put(key, errorArray.get(0).toString());
                            }
                            break;
                    }
                    this.delegate.onCashierValidateFaild(hashMapValidateError);
                    return;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        this.delegate.onCashierConnectFaild();
        return;
    }
}