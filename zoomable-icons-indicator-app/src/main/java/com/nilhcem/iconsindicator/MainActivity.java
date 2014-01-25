package com.nilhcem.iconsindicator;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import com.nilhcem.iconsindicator.viewpager.ViewPagerAdapter;
import com.nilhcem.iconsindicator.viewpager.ZoomOutPageTransformer;
import com.nilhcem.iconsindicator.viewpager.indicator.ZoomableIconsIndicator;

public class MainActivity extends ActionBarActivity {

    private ViewPager mViewPager;
    private ViewPagerAdapter mViewPagerAdapter;
    private ZoomableIconsIndicator mViewPagerIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        mViewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mViewPager.setAdapter(mViewPagerAdapter);
        mViewPager.setPageTransformer(true, new ZoomOutPageTransformer());
        mViewPager.setOffscreenPageLimit(mViewPagerAdapter.getCount());

        mViewPagerIndicator = (ZoomableIconsIndicator) findViewById(R.id.indicator);
        mViewPagerIndicator.setViewPager(mViewPager);
    }
}
