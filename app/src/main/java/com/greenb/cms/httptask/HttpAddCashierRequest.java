package com.greenb.cms.httptask;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.greenb.cms.api.CoreApi;
import com.greenb.cms.httpinterface.CashierInterface;
import com.greenb.cms.models.Cashier;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by HieuNguyen on 7/30/2015.
 */
public class HttpAddCashierRequest extends AsyncTask<JSONObject, Void, JSONObject> {
    public CashierInterface delegate;
    private Context context;
    private final String mAuthorization;

    public HttpAddCashierRequest(Context context, String authorization, CashierInterface delegate) {
        this.context = context;
        this.delegate = delegate;
        mAuthorization = authorization;
    }

    @Override
    protected JSONObject doInBackground(JSONObject... params) {
        return CoreApi.AddCashier(params[0], this.mAuthorization);
    }

    @Override
    protected void onPostExecute(JSONObject jsonObject) {
        Log.i("Cashier", jsonObject.toString());
    }
}