package com.bignerdranch.android.triplogger.database;

public class TripDbSchema {
    public static final class TripTable {
        public static final String NAME = "trips";

        public static final class Cols {
            public static final String UUID = "uuid";
            public static final String TITLE = "title";
            public static final String DATE = "date";
            public static final String DESTINATION = "destination";
            public static final String DURATION = "duration";
        }
    }
}
