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
import android.widget.ListView;

import com.greenb.cms.R;
import com.greenb.cms.XListView.view.XListView;
import com.greenb.cms.activity.ViewCashierActivity;
import com.greenb.cms.adapter.CashierAdapter;
import com.greenb.cms.httpinterface.GetCashierInterface;
import com.greenb.cms.httptask.HttpGetCashiersRequest;
import com.greenb.cms.models.Cashier;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by HieuNguyen on 7/28/2015.
 */
public class StaffListViewFragment extends Fragment implements XListView.IXListViewListener, GetCashierInterface, View.OnClickListener, ListView.OnItemClickListener {
    View rootView;
    private XListView xListViewCashiers;
    private String mUid, mToken;
    private int mPages, mPage;
    private Handler mHandler;
    private ArrayList<Cashier> mCashierArrList;
    private CashierAdapter mCashiersAdapter;
    private View mCashiersErrorView;
    private View mCashiersContentView;
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
        this.rootView = inflater.inflate(R.layout.fragment_staff_listview, container, false);
        this.xListViewCashiers = (XListView) rootView.findViewById(R.id.xListViewCashiers);
        this.mCashiersErrorView = rootView.findViewById(R.id.layout_error);
        this.mCashiersContentView = rootView.findViewById(R.id.layout_content);
        this.mCashierArrList = null;
        this.mCashiersAdapter = null;
        this.xListViewCashiers.setPullLoadEnable(true);
        this.xListViewCashiers.setFooterDividersEnabled(false);
        this.mImageReload = (ImageView) rootView.findViewById(R.id.reload_stafflist);
        this.mImageReload.setOnClickListener(this);

        this.getCashiers(this.mPage + 1);

        return this.rootView;
    }

    private void getCashiers(int page) {
        HttpGetCashiersRequest getCashierRequest = new HttpGetCashiersRequest(getActivity(), this.mUid + " " + this.mToken, this);
        getCashierRequest.execute(String.valueOf(page));
    }

    private void onLoad() {
        this.xListViewCashiers.stopRefresh();
        this.xListViewCashiers.stopLoadMore();
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
        Date date = new Date();
        this.xListViewCashiers.setRefreshTime(dateFormat.format(date));
    }

    @Override
    public void onRefresh() {
        this.mPages = 1;
        this.mPage = 0;
        this.mCashierArrList.clear();
        this.getCashiers(this.mPage + 1);
        this.onLoad();
    }

    @Override
    public void onLoadMore() {
        Log.i("expreess", String.valueOf(this.mPage <= (this.mPages - 1)));
        Log.i("expreess", String.valueOf(this.mPage <= (this.mPages - 1)));
        if (this.mPage <= this.mPages - 1) {

            this.getCashiers(this.mPage + 1);
        }else {
            this.xListViewCashiers.stopLoadMore();
        }
    }

    @Override
    public void onCashiersReceive(ArrayList<Cashier> cashiers, int pages) {
        if (cashiers.size() > 0) {
            this.mPages = pages;
            this.mPage = this.mPage + 1;

            if (this.mCashierArrList == null) {
                this.mCashierArrList = cashiers;
            } else {
                this.mCashierArrList.addAll(cashiers);
            }

            if (this.mCashiersAdapter == null) {
                this.mCashiersAdapter = new CashierAdapter(getActivity(), R.layout.xlist_cashier_view, this.mCashierArrList);
                this.xListViewCashiers.setAdapter(this.mCashiersAdapter);
                this.xListViewCashiers.setXListViewListener(this);
                this.xListViewCashiers.setOnItemClickListener(this);
            } else {
                this.mCashiersAdapter.notifyDataSetChanged();
                this.onLoad();
            }
        }
    }

    @Override
    public void onCashierGetFaild() {
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

            mCashiersContentView.setVisibility(show ? View.GONE : View.VISIBLE);
            mCashiersContentView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mCashiersContentView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mCashiersErrorView.setVisibility(show ? View.VISIBLE : View.GONE);
            mCashiersErrorView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mCashiersErrorView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mCashiersErrorView.setVisibility(show ? View.VISIBLE : View.GONE);
            mCashiersContentView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.reload_stafflist:
                this.showProgress(false);
                this.getCashiers(this.mPage + 1);
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Cashier cashier = (Cashier) parent.getItemAtPosition(position);
        Intent intent = new Intent(getActivity(), ViewCashierActivity.class);
        intent.putExtra("Cashier", (Serializable) cashier);
        getActivity().startActivity(intent);
    }
}
