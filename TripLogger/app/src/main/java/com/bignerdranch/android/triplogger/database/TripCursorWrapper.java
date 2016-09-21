package com.bignerdranch.android.triplogger.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.bignerdranch.android.triplogger.Trip;

import java.util.Date;
import java.util.UUID;

import com.bignerdranch.android.triplogger.database.TripDbSchema.TripTable;

public class TripCursorWrapper extends CursorWrapper {
    public TripCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Trip getTrip() {
        String uuidString = getString(getColumnIndex(TripTable.Cols.UUID));
        String title = getString(getColumnIndex(TripTable.Cols.TITLE));
        String destination = getString(getColumnIndex(TripTable.Cols.DESTINATION));
        long date = getLong(getColumnIndex(TripTable.Cols.DATE));
        String duration = getString(getColumnIndex(TripTable.Cols.DURATION));

        Trip trip = new Trip(UUID.fromString(uuidString));
        trip.setTitle(title);
        trip.setDestination(destination);
        trip.setDate(new Date(date));
        trip.setDuration(duration);

        return trip;
    }
}
