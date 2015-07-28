package com.greenb.cms.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.greenb.cms.R;
import com.greenb.cms.fragment.StaffListViewFragment;

/**
 * Created by HieuNguyen on 7/28/2015.
 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class MainTabPagerAdapter extends FragmentPagerAdapter {

    public static int[] mTabs = new int[]{R.drawable.ic_action_group, R.drawable.ic_action_product};

    public MainTabPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment sectionFragment = null;
        switch (position) {
            case 1:
                sectionFragment = new StaffListViewFragment();
                break;
            default:
//                sectionFragment = new OrderDetailInfoFragment();
                break;
        }

        return sectionFragment;
    }

    @Override
    public int getCount() {
        // Show 3 total pages.
        return mTabs.length;
    }
}