package com.nilhcem.iconsindicator.viewpager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.nilhcem.iconsindicator.R;

public class DummyFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";
    private static final String ARG_BACKGROUND_COLOR = "background_color";

    private int mSectionNumber;
    private int mBackgroundColor;

    /**
     * Returns a new instance of this fragment for the given section number.
     */
    public static DummyFragment newInstance(int sectionNumber, int backgroundColor) {
        DummyFragment fragment = new DummyFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        args.putInt(ARG_BACKGROUND_COLOR, backgroundColor);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        mSectionNumber = arguments.getInt(ARG_SECTION_NUMBER);
        mBackgroundColor = arguments.getInt(ARG_BACKGROUND_COLOR);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.viewpager_fragment, container, false);
        rootView.setBackgroundColor(mBackgroundColor);

        TextView textView = (TextView) rootView.findViewById(R.id.section_label);
        textView.setText(Integer.toString(mSectionNumber));

        return rootView;
    }
}
