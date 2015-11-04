package com.example.archana.congress.models;

/**
 * Created by Guest on 11/3/15.
 */
public class Representative {

    private String mName;
    private String mParty;
    private String mGender;
    private String mBirthday;

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getParty() {
        return mParty;
    }

    public void setParty(String party) {
        mParty = party;
    }

    public String getGender() {
        return mGender;
    }

    public void setGender(String gender) {
        mGender = gender;
    }

    public String getBirthday() {
        return mBirthday;
    }

    public void setBirthday(String birthday) {
        mBirthday = birthday;
    }
}
