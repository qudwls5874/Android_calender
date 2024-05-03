package com.example.myapplication.dataclass;

import android.graphics.Bitmap;

public class UserProfile {

    private String profileId;
    private Bitmap proifle;

    public UserProfile() {
    }

    public UserProfile(String profileId, Bitmap proifle) {
        this.profileId = profileId;
        this.proifle = proifle;
    }

    public String getProfileId() {
        return profileId;
    }

    public void setProfileId(String profileId) {
        this.profileId = profileId;
    }

    public Bitmap getProifle() {
        return proifle;
    }

    public void setProifle(Bitmap proifle) {
        this.proifle = proifle;
    }
}
