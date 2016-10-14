package com.bignerdranch.android.triplogger;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;


public class SettingsActivity extends AppCompatActivity {
    private ViewPager mViewPager;
    private Settings mSettings;

    public static Intent settings (Context packageContext, String settingsId) {
        Intent intent = new Intent(packageContext, SettingsActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trip_pager);

        mViewPager = (ViewPager) findViewById(R.id.activity_trip_pager_view_pager);

        mSettings = TripManager.get(this).getSettings();
        FragmentManager fragmentManager = getSupportFragmentManager();

        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {

            @Override
            public Fragment getItem(int position) {
                String id = mSettings.getId();
                return SettingsFragment.newInstance(id);
            }

            @Override
            public int getCount() {
                return 1;
            }
        });

        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) { }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) { }
        });

    }
}
