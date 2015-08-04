package com.greenb.cms.httpinterface;

import com.greenb.cms.models.Cashier;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by mrbadao on 03/05/2015.
 */
public interface AddCashierInterface {
    void onCashierAddSuccessly(Cashier cashier);
    void onCashierValidateFaild( HashMap<String,String> hashMapValidateError);
    void onCashierConnectFaild();
}
