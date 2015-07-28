package com.greenb.cms.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.greenb.cms.R;

/**
 * Created by HieuNguyen on 7/28/2015.
 */
public class FruitMaterialsListvViewFragment extends Fragment {
    View rootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.rootView = inflater.inflate(R.layout.fragment_fruitmeterials_listview, container, false);
        return this.rootView;
    }
}
