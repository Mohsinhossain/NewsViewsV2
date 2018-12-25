package com.mohsinmonad.newsviews.pageradapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.mohsinmonad.newsviews.fragmentpage.Pager1;

public class SectionsPagerAdapter extends FragmentPagerAdapter {

    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        // Return a PlaceholderFragment (defined as a static inner class below).
        return Pager1.newInstance(position + 1);

    }

    @Override
    public int getCount() {
        // Show 3 total pages.
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "SECTION Myself";
            case 1:
                return "SECTION NewsViews";
            case 2:
                return "SECTION Rokomari";
        }
        return null;
    }
}
