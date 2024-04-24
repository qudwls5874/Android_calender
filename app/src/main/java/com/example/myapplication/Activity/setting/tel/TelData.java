package com.example.myapplication.Activity.setting.tel;

import android.graphics.Bitmap;

import java.util.List;

public class TelData {

    private int id;
    private String name;
    private List<String> tel;
    private Bitmap profile;

    public TelData(int id, String name, List<String> tel, Bitmap profile){
        this.id = id;
        this.name = name;
        this.tel = tel;
        this.profile = profile;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getTel() {
        return tel;
    }

    public void setTel(List<String> tel) {
        this.tel = tel;
    }

    public Bitmap getProfile() {
        return profile;
    }

    public void setProfile(Bitmap profile) {
        this.profile = profile;
    }
}
