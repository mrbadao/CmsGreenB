package com.greenb.cms.fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;

import com.greenb.cms.R;
import com.greenb.cms.XListView.view.XListView;
import com.greenb.cms.activity.ViewCashierActivity;
import com.greenb.cms.adapter.CashierAdapter;
import com.greenb.cms.adapter.FruitAdapter;
import com.greenb.cms.httpinterface.GetFruitInterface;
import com.greenb.cms.httptask.HttpGetFruitsRequest;
import com.greenb.cms.models.Cashier;
import com.greenb.cms.models.Fruit;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by HieuNguyen on 7/28/2015.
 */
public class FruitMaterialsListvViewFragment extends Fragment implements XListView.IXListViewListener, GetFruitInterface {
    View rootView;
    private XListView xListViewFruits;
    private String mUid, mToken;
    private int mPages, mPage;
    private Handler mHandler;
    private ArrayList<Fruit> mFruitArrList;
    private FruitAdapter mFruitsAdapter;
//    private View mCashiersErrorView;
//    private View mCashiersContentView;
    private ImageView mImageReload;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity());
        this.mPage = 0;
        this.mPages = 1;
        this.mUid = sharedPref.getString("com.greenb.cms.user.uid", "");
        this.mToken = sharedPref.getString("com.greenb.cms.user.token", "");
        Log.i("Token", this.mUid + " " + this.mToken);
        this.rootView = inflater.inflate(R.layout.fragment_fruitmeterials_listview, container, false);
        this.xListViewFruits = (XListView) rootView.findViewById(R.id.xListViewFruits);
//        this.mCashiersErrorView = rootView.findViewById(R.id.layout_error);
//        this.mCashiersContentView = rootView.findViewById(R.id.layout_content);
        this.mFruitArrList = null;
        this.mFruitsAdapter = null;
        this.xListViewFruits.setPullLoadEnable(true);
        this.xListViewFruits.setFooterDividersEnabled(false);
//        this.mImageReload = (ImageView) rootView.findViewById(R.id.reload_stafflist);
//        this.mImageReload.setOnClickListener(this);

        this.getFruits(this.mPage + 1);

        return this.rootView;
    }

    private void getFruits(int page) {
        HttpGetFruitsRequest getFruitRequest = new HttpGetFruitsRequest(getActivity(), this.mUid + " " + this.mToken, this);
        getFruitRequest.execute(String.valueOf(page));
    }

    private void onLoad() {
        this.xListViewFruits.stopRefresh();
        this.xListViewFruits.stopLoadMore();
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
        Date date = new Date();
        this.xListViewFruits.setRefreshTime(dateFormat.format(date));
    }

    @Override
    public void onRefresh() {
        this.mPages = 1;
        this.mPage = 0;
        this.mFruitArrList.clear();
        this.getFruits(this.mPage + 1);
        this.onLoad();
    }

    @Override
    public void onLoadMore() {
        Log.i("expreess", String.valueOf(this.mPage <= (this.mPages - 1)));
        Log.i("expreess", String.valueOf(this.mPage <= (this.mPages - 1)));
        if (this.mPage <= this.mPages - 1) {

            this.getFruits(this.mPage + 1);
        }else {
            this.xListViewFruits.stopLoadMore();
        }
    }

    @Override
    public void onFruitsReceive(ArrayList<Fruit> fruits, int pages) {
        if (fruits.size() > 0) {
            this.mPages = pages;
            this.mPage = this.mPage + 1;

            if (this.mFruitArrList == null) {
                this.mFruitArrList = fruits;
            } else {
                this.mFruitArrList.addAll(fruits);
            }

            if (this.mFruitsAdapter == null) {
                this.mFruitsAdapter = new FruitAdapter(getActivity(), R.layout.xlist_fruit_view, this.mFruitArrList);
                this.xListViewFruits.setAdapter(this.mFruitsAdapter);
                this.xListViewFruits.setXListViewListener(this);
//                this.xListViewFruits.setOnItemClickListener(this);
            } else {
                this.mFruitsAdapter.notifyDataSetChanged();
                this.onLoad();
            }
        }
    }

    @Override
    public void onFruitsGetFaild() {
        showProgress(true);
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

//            mCashiersContentView.setVisibility(show ? View.GONE : View.VISIBLE);
//            mCashiersContentView.animate().setDuration(shortAnimTime).alpha(
//                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
//                @Override
//                public void onAnimationEnd(Animator animation) {
//                    mCashiersContentView.setVisibility(show ? View.GONE : View.VISIBLE);
//                }
//            });
//
//            mCashiersErrorView.setVisibility(show ? View.VISIBLE : View.GONE);
//            mCashiersErrorView.animate().setDuration(shortAnimTime).alpha(
//                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
//                @Override
//                public void onAnimationEnd(Animator animation) {
//                    mCashiersErrorView.setVisibility(show ? View.VISIBLE : View.GONE);
//                }
//            });
        } else {
//             The ViewPropertyAnimator APIs are not available, so simply show
//             and hide the relevant UI components.
//            mCashiersErrorView.setVisibility(show ? View.VISIBLE : View.GONE);
//            mCashiersContentView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

//    @Override
//    public void onClick(View v) {
//        int id = v.getId();
//        switch (id){
//            case R.id.reload_stafflist:
//                this.showProgress(false);
//                this.getFruits(this.mPage + 1);
//                break;
//        }
//    }

//    @Override
//    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        Cashier cashier = (Cashier) parent.getItemAtPosition(position);
//        Intent intent = new Intent(getActivity(), ViewCashierActivity.class);
//        intent.putExtra("Cashier", (Serializable) cashier);
//        getActivity().startActivity(intent);
//    }
}
