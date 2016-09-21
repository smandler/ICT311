package com.bignerdranch.android.triplogger;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.List;
import java.util.UUID;

public class TripActivity extends AppCompatActivity {
    private static final String EXTRA_TRIP_ID =
            "com.bignerdranch.android.triplogger.trip_id";

    private ViewPager mViewPager;
    private List<Trip> mTrips;

    public static Intent newIntent(Context packageContext, UUID tripId) {
        Intent intent = new Intent(packageContext, TripActivity.class);
        intent.putExtra(EXTRA_TRIP_ID, tripId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trip_pager);

        UUID tripId = (UUID) getIntent()
                .getSerializableExtra(EXTRA_TRIP_ID);

        mViewPager = (ViewPager) findViewById(R.id.activity_trip_pager_view_pager);

        mTrips = TripManager.get(this).getTrips();
        FragmentManager fragmentManager = getSupportFragmentManager();

        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {

            @Override
            public Fragment getItem(int position) {
                Trip trip = mTrips.get(position);
                return TripFragment.newInstance(trip.getId());
            }

            @Override
            public int getCount() {
                return mTrips.size();
            }
        });

        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) { }

            @Override
            public void onPageSelected(int position) {
                Trip trip = mTrips.get(position);
                if (trip.getTitle() != null) {
                    setTitle(trip.getTitle());
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) { }
        });

        for (int i = 0; i < mTrips.size(); i++) {
            if (mTrips.get(i).getId().equals(tripId)) {
                mViewPager.setCurrentItem(i);
                break;
            }
        }
    }
}
