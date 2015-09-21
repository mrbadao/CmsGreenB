package com.greenb.cms.models;

import java.io.Serializable;

/**
 * Created by HieuNguyen on 8/3/2015.
 */
public class Fruit implements Serializable{
    public String id;
    public String name;
    public String image_name;
    public int Kcal;
    public int Fiber;
    public int VA;
    public int VB;
    public int VB1;
    public int VB2;
    public int VC;
    public int VE;
    public int Ca;
    public int Ka;

    public Fruit()
    {
        this.id = "";
        this.name = "";
        this.image_name = "";
        this.Kcal = this.Fiber = this.VA = this.VB = this.VB1 = this.VB2 = this.VE = this.Ca = this.VC = this.Ka = 0;

    }
}
