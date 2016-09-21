package com.bignerdranch.android.triplogger;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

public class TripListFragment extends Fragment {

    private static final String SAVED_SUBTITLE_VISIBLE = "subtitle";

    private RecyclerView mTripRecyclerView;
    private TripAdapter mAdapter;
    private boolean mSubtitleVisible;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trip_list, container, false);

        mTripRecyclerView = (RecyclerView) view
                .findViewById(R.id.trip_recycler_view);
        mTripRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        if (savedInstanceState != null) {
            mSubtitleVisible = savedInstanceState.getBoolean(SAVED_SUBTITLE_VISIBLE);
        }

        updateUI();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(SAVED_SUBTITLE_VISIBLE, mSubtitleVisible);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_trip_list, menu);

        MenuItem subtitleItem = menu.findItem(R.id.menu_item_settings);
        if (mSubtitleVisible) {
            subtitleItem.setTitle(R.string.hide_subtitle);
        } else {
            subtitleItem.setTitle(R.string.show_subtitle);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_new_trip:
                Trip trip = new Trip();
                TripManager.get(getActivity()).addTrip(trip);
                Intent intent = TripActivity
                        .newIntent(getActivity(), trip.getId());
                startActivity(intent);
                return true;
            case R.id.menu_item_settings:
                mSubtitleVisible = !mSubtitleVisible;
                getActivity().invalidateOptionsMenu();
                updateSubtitle();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void updateSubtitle() {
        TripManager tripManager = TripManager.get(getActivity());
        int tripCount = tripManager.getTrips().size();
        String subtitle = getString(R.string.subtitle_format, tripCount);

        if (!mSubtitleVisible) {
            subtitle = null;
        }

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.getSupportActionBar().setSubtitle(subtitle);
    }

    private void updateUI() {
        TripManager tripManager = TripManager.get(getActivity());
        List<Trip> trips = tripManager.getTrips();

        if (mAdapter == null) {
            mAdapter = new TripAdapter(trips);
            mTripRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.setTrips(trips);
            mAdapter.notifyDataSetChanged();
        }

        updateSubtitle();
    }

    private class TripHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        private TextView mTitleTextView;
        private TextView mDateTextView;
        private TextView mDestinationTextView;
        private CheckBox mSolvedCheckBox;

        private Trip mTrip;

        public TripHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            mTitleTextView = (TextView) itemView.findViewById(R.id.list_item_trip_title_text_view);
            mDateTextView = (TextView) itemView.findViewById(R.id.list_item_trip_date_text_view);
            mDestinationTextView = (TextView) itemView.findViewById(R.id.list_item_trip_destination_text_view);
        }

        public void bindTrip(Trip trip) {
            mTrip = trip;
            mTitleTextView.setText(mTrip.getTitle());
            mDestinationTextView.setText(mTrip.getDestination());
            mDateTextView.setText(mTrip.getDate().toString());
        }

        @Override
        public void onClick(View v) {
            Intent intent = TripActivity.newIntent(getActivity(), mTrip.getId());
            startActivity(intent);
        }
    }

    private class TripAdapter extends RecyclerView.Adapter<TripHolder> {

        private List<Trip> mTrips;

        public TripAdapter(List<Trip> trips) {
            mTrips = trips;
        }

        @Override
        public TripHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.list_item_trip, parent, false);
            return new TripHolder(view);
        }

        @Override
        public void onBindViewHolder(TripHolder holder, int position) {
            Trip trip = mTrips.get(position);
            holder.bindTrip(trip);
        }

        @Override
        public int getItemCount() {
            return mTrips.size();
        }

        public void setTrips(List<Trip> trips) {
            mTrips = trips;
        }
    }
}
