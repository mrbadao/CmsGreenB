package com.greenb.cms.httpinterface;

import org.json.JSONObject;

/**
 * Created by mrbadao on 03/05/2015.
 */
public interface LoginInterface {
    void onUserLoginSuccess();
    void onUserLoginFaild(String mMessage);
}
