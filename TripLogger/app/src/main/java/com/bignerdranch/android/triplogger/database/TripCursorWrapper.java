package com.bignerdranch.android.triplogger.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.bignerdranch.android.triplogger.Trip;
import com.bignerdranch.android.triplogger.Settings;

import java.util.Date;
import java.util.UUID;

import com.bignerdranch.android.triplogger.database.TripDbSchema.TripTable;
import com.bignerdranch.android.triplogger.database.TripDbSchema.SettingsTable;


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
        String comment = getString(getColumnIndex(TripTable.Cols.COMMENT));
        String type = getString(getColumnIndex(TripTable.Cols.TYPE));
        String lat = getString(getColumnIndex(TripTable.Cols.LAT));
        String lon = getString(getColumnIndex(TripTable.Cols.LON));

        Trip trip = new Trip(UUID.fromString(uuidString));
        trip.setTitle(title);
        trip.setDestination(destination);
        trip.setDate(new Date(date));
        trip.setDuration(duration);
        trip.setComment(comment);
        trip.setType(type);
        trip.setLatitude(lat);
        trip.setLongtitude(lon);

        return trip;
    }

    public Settings getSettings() {
        String id = getString(getColumnIndex(SettingsTable.Cols.ID));
        String name = getString(getColumnIndex(SettingsTable.Cols.NAME));
        String email = getString(getColumnIndex(SettingsTable.Cols.EMAIL));
        String gender = getString(getColumnIndex(SettingsTable.Cols.GENDER));
        String comment = getString(getColumnIndex(SettingsTable.Cols.COMMENT));

        Settings settings = new Settings();
        settings.setId(id);
        settings.setName(name);
        settings.setEmail(email);
        settings.setGender(gender);
        settings.setComment(comment);

        return settings;
    }
}
