package com.greenb.cms.httptask;

import android.content.Context;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.greenb.cms.api.CoreApi;
import com.greenb.cms.httpinterface.GetCashierInterface;
import com.greenb.cms.models.Cashier;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by HieuNguyen on 7/30/2015.
 */
public class HttpGetCashiersRequest extends AsyncTask<String, Void, JSONObject> {
    public GetCashierInterface delegate;
    private Context context;
    private final String mAuthorization;

    public HttpGetCashiersRequest(Context context, String authorization, GetCashierInterface delegate) {
        this.context = context;
        this.delegate = delegate;
        mAuthorization = authorization;
    }

    @Override
    protected JSONObject doInBackground(String... params) {
        return CoreApi.GetCashier(params[0], this.mAuthorization);
    }

    @Override
    protected void onPostExecute(JSONObject jsonObject) {
        ArrayList<Cashier> cashiers = new ArrayList<Cashier>();
        int pages = 1;
        try {
            if (jsonObject != null && !jsonObject.isNull("success") && jsonObject.getBoolean("success")) {
                jsonObject = jsonObject.getJSONObject("data");
                JSONArray jsonCashiersArr = jsonObject.getJSONArray("cashiers");
                pages = jsonObject.getInt("pages");
                Gson gson = new Gson();

                for (int i = 0; i < jsonCashiersArr.length(); i++) {
                    cashiers.add(gson.fromJson(jsonCashiersArr.get(i).toString(), Cashier.class));
                }
            }
            else {
                this.delegate.onCashierGetFaild();
                return;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            this.delegate.onCashierGetFaild();
            return;
        }
        this.delegate.onCashiersReceive(cashiers, pages);
    }
}