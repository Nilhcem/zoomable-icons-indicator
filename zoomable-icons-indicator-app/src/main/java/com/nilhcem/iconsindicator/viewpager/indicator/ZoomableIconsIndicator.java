package com.nilhcem.iconsindicator.viewpager.indicator;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.nilhcem.iconsindicator.R;
import com.nineoldandroids.view.ViewHelper;

import static android.support.v4.view.ViewPager.OnPageChangeListener;

public class ZoomableIconsIndicator extends LinearLayout implements PageIndicator {

    private static final float SCALE_DEFAULT = 1.0f;
    private static final float SCALE_MAX = 1.3f;
    private static final float SCALE_GAP = SCALE_MAX - SCALE_DEFAULT;

    private ViewPager mViewPager;
    private OnPageChangeListener mListener;

    private LinearLayout mIconsLayout;
    private View mIcons[];
    private int mIndicatorPositions[];

    private ImageView mSubIndicator;
    private int mSubIndicatorHalfWidth;

    private int mSelectedIndex;
    private int mGapBetweenElements;

    public ZoomableIconsIndicator(Context context) {
        this(context, null);
    }

    public ZoomableIconsIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (isInEditMode()) return;
        setPadding(0, 0, 0, 0);
        setOrientation(LinearLayout.VERTICAL);

        // Layout that should contain the icons
        mIconsLayout = new LinearLayout(context, attrs);
        mIconsLayout.setPadding(0, 0, 0, 0);
        mIconsLayout.setOrientation(LinearLayout.HORIZONTAL);
        addView(mIconsLayout, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

        // Sub-icon indicator arrow
        mSubIndicator = new ImageView(context, attrs);
        mSubIndicator.setPadding(0, 0, 0, 0);
        mSubIndicator.setImageResource(R.drawable.indicator);
        mSubIndicatorHalfWidth = mSubIndicator.getDrawable().getIntrinsicWidth() / 2;
        addView(mSubIndicator, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void setViewPager(ViewPager view) {
        if (mViewPager == view) {
            return;
        }
        if (mViewPager != null) {
            mViewPager.setOnPageChangeListener(null);
        }
        PagerAdapter adapter = view.getAdapter();
        if (adapter == null) {
            throw new IllegalStateException("ViewPager does not have adapter instance.");
        }
        mViewPager = view;
        view.setOnPageChangeListener(this);
        notifyDataSetChanged();
    }

    @Override
    public void setViewPager(ViewPager view, int initialPosition) {
        setViewPager(view);
        setCurrentItem(initialPosition);
    }

    @Override
    public void setCurrentItem(int item) {
        if (mViewPager == null) {
            throw new IllegalStateException("ViewPager has not been bound.");
        }
        mSelectedIndex = item;
        mViewPager.setCurrentItem(item);

        int tabCount = mIcons.length;
        for (int i = 0; i < tabCount; i++) {
            View child = mIcons[i];
            child.setSelected(i == item);
        }
    }

    @Override
    public void setOnPageChangeListener(OnPageChangeListener listener) {
        mListener = listener;
    }

    @Override
    public void notifyDataSetChanged() {
        mIconsLayout.removeAllViews();
        IconPagerAdapter iconAdapter = (IconPagerAdapter) mViewPager.getAdapter();
        int count = iconAdapter.getCount();
        mIcons = new View[count];
        mIndicatorPositions = new int[count];

        for (int i = 0; i < count; i++) {
            ImageView view = new ImageView(getContext());
            view.setImageResource(iconAdapter.getIconResId(i));
            view.setLayoutParams(new LayoutParams(0, LayoutParams.WRAP_CONTENT, 1));
            view.setOnClickListener(new OnZoomableIconClickListener(i));
            mIcons[i] = view;
            mIndicatorPositions[i] = -1;
            mIconsLayout.addView(view);
        }
        if (mSelectedIndex > count) {
            mSelectedIndex = count - 1;
        }
        setCurrentItem(mSelectedIndex);
        requestLayout();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        // Moves indicator
        ViewHelper.setX(mSubIndicator, getIndicatorPositionAt(position) + (positionOffset * mGapBetweenElements));

        // Scale items
        float gap = SCALE_GAP * positionOffset;
        scaleView(mIcons[position], SCALE_MAX - gap);
        if (positionOffset != 0.0f) {
            int nextPosition = position + 1;
            if (nextPosition < mIcons.length) {
                scaleView(mIcons[nextPosition], SCALE_DEFAULT + gap);
            }
        }

        if (mListener != null) {
            mListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
        }
    }

    @Override
    public void onPageSelected(int position) {
        setCurrentItem(position);
        if (mListener != null) {
            mListener.onPageSelected(position);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        if (mListener != null) {
            mListener.onPageScrollStateChanged(state);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (mGapBetweenElements == 0) {
            mGapBetweenElements = getMeasuredWidth() / mViewPager.getAdapter().getCount();
        }
    }

    /**
     * Returns the indicator X coordinate for the given position (with some caching for performance)
     */
    private int getIndicatorPositionAt(int index) {
        if (mIndicatorPositions[index] == -1) {
            mIndicatorPositions[index] = (index * mGapBetweenElements) + mGapBetweenElements / 2 - mSubIndicatorHalfWidth;
        }
        return mIndicatorPositions[index];
    }

    private static void scaleView(View view, float scale) {
        ViewHelper.setScaleX(view, scale);
        ViewHelper.setScaleY(view, scale);
    }

    class OnZoomableIconClickListener implements OnClickListener {
        private final int mPosition;

        public OnZoomableIconClickListener(int position) {
            mPosition = position;
        }

        @Override
        public void onClick(View v) {
            mViewPager.setCurrentItem(mPosition);
        }
    }
}
