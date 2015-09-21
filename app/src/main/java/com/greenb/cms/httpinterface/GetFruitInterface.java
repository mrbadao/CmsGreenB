package com.greenb.cms.httpinterface;

import com.greenb.cms.models.Cashier;
import com.greenb.cms.models.Fruit;

import java.util.ArrayList;

/**
 * Created by mrbadao on 03/05/2015.
 */
public interface GetFruitInterface {
    void onFruitsReceive(ArrayList<Fruit> cashiers, int pages);
    void onFruitsGetFaild();
}
