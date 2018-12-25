package com.mohsinmonad.newsviews.fragmentpage;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.mohsinmonad.newsviews.R;


public class Pager1 extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    public Pager1() {

    }

    public static Pager1 newInstance(int sectionNumber) {
        Pager1 fragment = new Pager1();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_pager1, container, false);

        return rootView;
    }
}
