package com.greenb.cms.models;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by HieuNguyen on 8/3/2015.
 */
public class Cashier implements Serializable{
    public String id;
    public String display_name;
    public String loginid;
    public String phone;
    public String email;
    public String status;
    public String created;
    public String modified;
    public String address;

    public Cashier(){
        id = display_name = loginid = phone = status = email = created = modified = address;
    }
}
