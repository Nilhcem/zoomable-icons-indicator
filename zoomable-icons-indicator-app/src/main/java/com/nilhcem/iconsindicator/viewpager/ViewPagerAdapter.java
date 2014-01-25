package com.nilhcem.iconsindicator.viewpager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.nilhcem.iconsindicator.R;
import com.viewpagerindicator.IconPagerAdapter;

public class ViewPagerAdapter extends FragmentPagerAdapter implements IconPagerAdapter {

    private static final int[] ICONS = {R.drawable.ic_1, R.drawable.ic_2, R.drawable.ic_3, R.drawable.ic_4};
    private static final int[] BG_COLORS = {0xff84869d, 0xff7e7d98, 0xff706f80, 0xff7a6f80};

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        return DummyFragment.newInstance(i + 1, BG_COLORS[i]);
    }

    @Override
    public int getIconResId(int index) {
        return ICONS[index];
    }

    @Override
    public int getCount() {
        return ICONS.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return super.getPageTitle(position);
    }
}
