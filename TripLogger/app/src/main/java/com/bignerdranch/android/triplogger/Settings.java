package com.bignerdranch.android.triplogger;

import java.util.Date;
import java.util.UUID;

public class Settings {

    private String mId;
    private String mName;
    private String mEmail;
    private String mGender;
    private String mComment;

    public Settings() {
        this("1");
    }

    public Settings(String id) {
        mId = id;
    }
    public String getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }
    public void setName(String name) {
        mName = name;
    }

    public String getEmail() {
        return mEmail;
    }
    public void setEmail(String email) {
        mEmail = email;
    }

    public String getGender() {
        return mGender;
    }
    public void setGender(String gender) {
        mGender = gender;
    }

    public String getComment() {
        return mComment;
    }
    public void setComment(String comment) {
        mComment = comment;
    }

}
