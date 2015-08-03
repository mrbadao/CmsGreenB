package com.greenb.cms.adapter;

import android.content.Context;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.greenb.cms.R;
import com.greenb.cms.models.Cashier;

import java.util.ArrayList;

/**
 * Created by HieuNguyen on 7/28/2015.
 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class CashierAdapter extends ArrayAdapter {
    private Context mContext;
    private int mLyoutRes;
    private ArrayList<Cashier> mObjects;

    public CashierAdapter(Context context, int resource, ArrayList<Cashier> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mLyoutRes = resource;
        mObjects = objects;
    }

    @Override
    public int getCount() {
        return mObjects.size();
    }

    @Override
    public Object getItem(int position) {
        return mObjects.get(position);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = null;
        LayoutInflater mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            view = mInflater.inflate(mLyoutRes, parent, false);
        } else {
            view = convertView;
        }

        Cashier item = (Cashier) getItem(position);
        TextView txtOrderName = (TextView) view.findViewById(R.id.item_cashier_textview);

        txtOrderName.setText(item.loginid);
        return view;
    }
}