package com.greenb.cms.httpinterface;

import com.greenb.cms.models.Cashier;

import java.util.ArrayList;

/**
 * Created by mrbadao on 03/05/2015.
 */
public interface CashierInterface {
    void onCashiersReceive(ArrayList<Cashier> cashiers, int pages);
    void onCashierGetFaild();
}
