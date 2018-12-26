package com.mohsinmonad.newsviews.main;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.mohsinmonad.newsviews.R;
import com.mohsinmonad.newsviews.fragmentpage.Pager1;
import com.mohsinmonad.newsviews.fragmentpage.Pager2;
import com.mohsinmonad.newsviews.fragmentpage.Pager3;
import com.mohsinmonad.newsviews.home.HomeActivity;
import com.mohsinmonad.newsviews.pageradapter.SectionsPagerAdapter;
import com.mohsinmonad.newsviews.util.MyPreferences;

public class PagerActivity extends AppCompatActivity {

    SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    ImageButton mNextBtn;
    Button mSkipBtn, mFinishBtn;
    ImageView zero, one, two;
    ImageView[] indicators;
    int lastLeftValue = 0;

    CoordinatorLayout mCoordinator;

    static final String TAG = "PagerActivity";

    int page = 0;   //  to track page position

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pager);

        //Only once  the intro splash activity and second time it will show other activity
        SharedPreferences pref = getSharedPreferences("ActivityPREF", Context.MODE_PRIVATE);
        if (pref.getBoolean("activity_executed", false)) {
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
            finish();
        } else {
            SharedPreferences.Editor ed = pref.edit();
            ed.putBoolean("activity_executed", true);
            ed.apply();
        }

        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        mNextBtn = findViewById(R.id.intro_btn_next);

        mSkipBtn = findViewById(R.id.intro_btn_skip);
        mFinishBtn = findViewById(R.id.intro_btn_finish);

        zero = findViewById(R.id.intro_indicator_0);
        one = findViewById(R.id.intro_indicator_1);
        two = findViewById(R.id.intro_indicator_2);

        mCoordinator = findViewById(R.id.main_content);


        indicators = new ImageView[]{zero, one, two};

        // Set up the ViewPager with the sections adapter.
        mViewPager = findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        mViewPager.setCurrentItem(page);
        updateIndicators(page);

        //final int color1 = ContextCompat.getColor(this, R.color.cyan);

        //final int[] colorList = new int[]{color1, color2, color3};

        //final ArgbEvaluator evaluator = new ArgbEvaluator();

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                page = position;

                //updateIndicators(page);

                switch (position) {
                    case 0:
                        Fragment fragment1 = new Pager1();
                        loadFragment(fragment1);
                        break;
                    case 1:
                        Fragment fragment2 = new Pager2();
                        loadFragment(fragment2);
                        break;
                    case 2:
                        Fragment fragment3 = new Pager3();
                        loadFragment(fragment3);
                        break;
                }

                //color update
                //int colorUpdate = (Integer) evaluator.evaluate(positionOffset, colorList[position], colorList[position == 2 ? position : position + 1]);
                //mViewPager.setBackgroundColor(colorUpdate);

            }

            @Override
            public void onPageSelected(int position) {

                page = position;

                //updateIndicators(page);

                switch (position) {
                    case 0:
                        Fragment fragment1 = new Pager1();
                        loadFragment(fragment1);
                        //mViewPager.setBackgroundColor(color1);
                        break;
                    case 1:
                        Fragment fragment2 = new Pager2();
                        loadFragment(fragment2);
                        //mViewPager.setBackgroundColor(color2);
                        break;
                    case 2:
                        Fragment fragment3 = new Pager3();
                        loadFragment(fragment3);
                        //mViewPager.setBackgroundColor(color3);
                        break;
                }

                mNextBtn.setVisibility(position == 2 ? View.GONE : View.VISIBLE);
                mFinishBtn.setVisibility(position == 2 ? View.VISIBLE : View.GONE);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mNextBtn.setOnClickListener(v -> {
            page += 1;
            mViewPager.setCurrentItem(page, true);
        });

        mSkipBtn.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
            finish();
        });

        mFinishBtn.setOnClickListener(v -> {
            finish();
            //Utils.saveSharedSetting(PagerActivity.this, HomeActivity.PREF_USER_FIRST_TIME, "false");
            startActivity(new Intent(getApplicationContext(), HomeActivity.class));

        });
    }

    void updateIndicators(int position) {
        for (int i = 0; i < indicators.length; i++) {
            indicators[i].setBackgroundResource(
                    i == position ? R.drawable.indicator_selected : R.drawable.indicator_unselected
            );
        }
    }

    void loadFragment(Fragment fragment) {
        updateIndicators(page);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment, fragment.getClass().getSimpleName())
                .addToBackStack(null).commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

}