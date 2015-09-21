package com.greenb.cms.httptask;

import android.content.Context;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.greenb.cms.api.CoreApi;
import com.greenb.cms.httpinterface.GetCashierInterface;
import com.greenb.cms.httpinterface.GetFruitInterface;
import com.greenb.cms.models.Cashier;
import com.greenb.cms.models.Fruit;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by HieuNguyen on 7/30/2015.
 */
public class HttpGetFruitsRequest extends AsyncTask<String, Void, JSONObject> {
    public GetFruitInterface delegate;
    private Context context;
    private final String mAuthorization;

    public HttpGetFruitsRequest(Context context, String authorization, GetFruitInterface delegate) {
        this.context = context;
        this.delegate = delegate;
        mAuthorization = authorization;
    }

    @Override
    protected JSONObject doInBackground(String... params) {
        return CoreApi.GetFruit(params[0], this.mAuthorization);
    }

    @Override
    protected void onPostExecute(JSONObject jsonObject) {
        ArrayList<Fruit> fruits = new ArrayList<Fruit>();
        int pages = 1;
        try {
            if (jsonObject != null && !jsonObject.isNull("success") && jsonObject.getBoolean("success")) {
                pages = jsonObject.getInt("pages");
//                jsonObject = jsonObject.getJSONObject("data");
                JSONArray jsonCashiersArr = jsonObject.getJSONArray("data");
                Gson gson = new Gson();

                for (int i = 0; i < jsonCashiersArr.length(); i++) {
                    fruits.add(gson.fromJson(jsonCashiersArr.get(i).toString(), Fruit.class));
                }
            }
            else {
                this.delegate.onFruitsGetFaild();
                return;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            this.delegate.onFruitsGetFaild();
            return;
        }
        this.delegate.onFruitsReceive(fruits, pages);
    }
}