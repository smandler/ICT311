package com.bignerdranch.android.triplogger.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.bignerdranch.android.triplogger.database.TripDbSchema.TripTable;
import com.bignerdranch.android.triplogger.database.TripDbSchema.SettingsTable;

public class TripBaseHelper extends SQLiteOpenHelper {
    private static final String TAG = "TripBaseHelper";
    private static final int VERSION = 2;
    private static final String DATABASE_NAME = "tripBase.db";

    public TripBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table " + TripTable.NAME + "(" +
                " _id integer primary key autoincrement, " +
                TripTable.Cols.UUID + ", " +
                TripTable.Cols.TITLE + ", " +
                TripTable.Cols.DESTINATION + ", " +
                TripTable.Cols.DATE + ", " +
                TripTable.Cols.TYPE + ", " +
                TripTable.Cols.COMMENT + ", " +
                TripTable.Cols.DURATION +
                ")"
        );
        db.execSQL("create table " + SettingsTable.NAME + "(" +
                SettingsTable.Cols.ID + ", " +
                SettingsTable.Cols.NAME + ", " +
                SettingsTable.Cols.EMAIL + ", " +
                SettingsTable.Cols.GENDER + ", " +
                SettingsTable.Cols.COMMENT +
                ")"
        );
        // only one record/ only updates after
        db.execSQL("insert into " + SettingsTable.NAME + " values ('1', '', '', '0', '')"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
