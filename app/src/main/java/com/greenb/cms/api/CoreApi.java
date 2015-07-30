package com.greenb.cms.api;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by HieuNguyen on 7/30/2015.
 */
public class CoreApi {
    private static InputStream is = null;
    private static JSONObject jObj = null;
    private static String json = "";

    static final String API_DOMAIN = "http://192.168.1.16/greenbapi/";
    static final String REQUEST_AGENT = "Android";

    private static JSONObject getJSON(String uri, JSONObject postData, String authorization) {
        String Url = API_DOMAIN + uri;

        StringBuilder builder = new StringBuilder();
        HttpClient client = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(Url);


        try {
            JSONObject sendData = new JSONObject();
            HttpContext localContext = new BasicHttpContext();

            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");
            httpPost.setHeader("Authorization", authorization);

            if (postData != null) {
                sendData.put("data", postData);
            }

            sendData.put("Request-Agent", REQUEST_AGENT);
            StringEntity se = new StringEntity(sendData.toString());
            Log.i("JSONSENDPARAM", sendData.toString());
            httpPost.setEntity(se);

            HttpResponse response = client.execute(httpPost, localContext);

            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();

            if (statusCode == 200) {

                HttpEntity entity = response.getEntity();
                InputStream content = entity.getContent();

                BufferedReader reader = new BufferedReader(new InputStreamReader(content));

                String line;

                while ((line = reader.readLine()) != null) {
                    builder.append(line);
                }

            } else {
                return null;
            }

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(builder.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }


    public static JSONObject Login(String email, String password) {
        JSONObject jsonData = new JSONObject();
        try {
            jsonData.put("email", email);
            jsonData.put("password", password);
            return getJSON("auth/authenticate", jsonData, "Admin-Login");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
