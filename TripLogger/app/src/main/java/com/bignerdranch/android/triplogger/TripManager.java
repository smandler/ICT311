package com.bignerdranch.android.triplogger;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import com.bignerdranch.android.triplogger.database.TripBaseHelper;
import com.bignerdranch.android.triplogger.database.TripCursorWrapper;

import com.bignerdranch.android.triplogger.database.TripDbSchema.SettingsTable;
import com.bignerdranch.android.triplogger.database.TripDbSchema.TripTable;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TripManager {
    private static TripManager sTripManager;

    private Context mContext;
    private SQLiteDatabase mDatabase;

    public static TripManager get(Context context) {
        if (sTripManager == null) {
            sTripManager = new TripManager(context);
        }
        return sTripManager;
    }

    private TripManager(Context context) {
        mContext = context.getApplicationContext();
        mDatabase = new TripBaseHelper(mContext)
                .getWritableDatabase();
    }

    // working with trips
    private static ContentValues getTripValues(Trip trip) {
        ContentValues values = new ContentValues();
        values.put(TripTable.Cols.UUID, trip.getId().toString());
        values.put(TripTable.Cols.TITLE, trip.getTitle());
        values.put(TripTable.Cols.DESTINATION, trip.getDestination());
        values.put(TripTable.Cols.DATE, trip.getDate().getTime());
        values.put(TripTable.Cols.DURATION, trip.getDuration());
        values.put(TripTable.Cols.TYPE, trip.getType());
        values.put(TripTable.Cols.COMMENT, trip.getComment());

        return values;
    }

    public void addTrip(Trip c) {
        ContentValues values = getTripValues(c);

        mDatabase.insert(TripTable.NAME, null, values);
    }

    public void updateTrip(Trip trip) {
        String uuidString = trip.getId().toString();
        ContentValues values = getTripValues(trip);

        mDatabase.update(TripTable.NAME, values,
                TripTable.Cols.UUID + " = ?",
                new String[]{uuidString});
    }

    public void deleteTrip(Trip trip) {
        String uuidString = trip.getId().toString();
        ContentValues values = getTripValues(trip);

        mDatabase.delete(TripTable.NAME, TripTable.Cols.UUID + " = ?",
                new String[]{uuidString});
    }

    private TripCursorWrapper queryTrips(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                TripTable.NAME,
                null, // Columns - null selects all columns
                whereClause,
                whereArgs,
                null, // groupBy
                null, // having
                null  // orderBy
        );

        return new TripCursorWrapper(cursor);
    }


    public List<Trip> getTrips() {
        List<Trip> trips = new ArrayList<>();

        TripCursorWrapper cursor = queryTrips(null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            trips.add(cursor.getTrip());
            cursor.moveToNext();
        }
        cursor.close();

        return trips;
    }

    public Trip getTrip(UUID id) {
        TripCursorWrapper cursor = queryTrips(
                TripTable.Cols.UUID + " = ?",
                new String[]{id.toString()}
        );

        try {
            if (cursor.getCount() == 0) {
                return null;
            }

            cursor.moveToFirst();
            return cursor.getTrip();
        } finally {
            cursor.close();
        }
    }

    public File getPhotoFile(Trip trip) {
        File externalFilesDir = mContext
                .getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        if (externalFilesDir == null) {
            return null;
        }
        return new File(externalFilesDir, trip.getPhotoFileName());
    }

    // working with settings - only one record

    private ContentValues getSettingsValues(Settings settings) {
        ContentValues values = new ContentValues();

        values.put(SettingsTable.Cols.ID, settings.getId());
        values.put(SettingsTable.Cols.NAME, settings.getName());
        values.put(SettingsTable.Cols.EMAIL, settings.getEmail());
        values.put(SettingsTable.Cols.GENDER, settings.getGender());
        values.put(SettingsTable.Cols.COMMENT, settings.getComment());

        return values;
    }

    public void updateSettings(Settings settings) {
        ContentValues values = getSettingsValues(settings);
        String id = settings.getId();
        mDatabase.update(SettingsTable.NAME, values,
                SettingsTable.Cols.ID + " = ?",
                new String[]{id});
    }

    private TripCursorWrapper querySettings() {
        Cursor cursor = mDatabase.query(
                SettingsTable.NAME,
                null, // Columns - null selects all columns
                null,
                null,
                null, // groupBy
                null, // having
                null  // orderBy
        );

        return new TripCursorWrapper(cursor);
    }

    public Settings getSettings() {
        TripCursorWrapper cursor = querySettings();

        try {
            if (cursor.getCount() == 0) {
                return null;
            }

            cursor.moveToFirst();
            return cursor.getSettings();
        } finally {
            cursor.close();
        }
    }


}