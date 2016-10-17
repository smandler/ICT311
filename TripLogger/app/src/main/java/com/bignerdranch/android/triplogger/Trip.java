package com.bignerdranch.android.triplogger;

import java.util.Date;
import java.util.UUID;

public class Trip {

    private UUID mId;
    private String mTitle;
    private String mDestination;
    private String mDuration;
    private String mType;
    private String mLat;
    private String mLon;
    private String mComment;
    private Date mDate;

    public Trip() {
        this(UUID.randomUUID());
    }

    public Trip(UUID id) {
        mId = id;
        mDate = new Date();
    }
    public UUID getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }
    public void setTitle(String title) {
        mTitle = title;
    }

    public String getDestination() {
        return mDestination;
    }
    public void setDestination(String destination) {
        mDestination = destination;
    }

    public Date getDate() {
        return mDate;
    }
    public void setDate(Date date) { mDate = date; }

    public String getLongtitude() { return mLon; }
    public void setLongtitude(String lon) { mLon = lon; }

    public String getLatitude() {
        return mLat;
    }
    public void setLatitude(String lat) { mLat = lat; }

    public String getType() {
        return mType;
    }
    public void setType(String type) { mType = type; }

    public String getComment() {
        return mComment;
    }
    public void setComment(String comment) {
        mComment = comment;
    }

    public String getDuration() {
        return mDuration;
    }
    public void setDuration(String duration) {
        mDuration = duration;
    }

    public String getPhotoFileName() { return "TRIP_" + getId().toString() + ".jpg"; }
}
