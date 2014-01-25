package com.nilhcem.iconsindicator.viewpager;

import android.support.v4.view.ViewPager;
import android.view.View;
import com.nineoldandroids.view.ViewHelper;

public class ZoomOutPageTransformer implements ViewPager.PageTransformer {

    @Override
    public void transformPage(View view, float position) {
        if (position >= -1 && position <= 1) {
            float scaleFactor = 1 - Math.abs(position * 0.3f);
            ViewHelper.setScaleX(view, scaleFactor);
            ViewHelper.setScaleY(view, scaleFactor);
        }
    }
}
