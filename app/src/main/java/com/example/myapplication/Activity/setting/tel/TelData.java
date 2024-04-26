package com.example.myapplication.Activity.setting.tel;

import android.graphics.Bitmap;

import java.util.List;

public class TelData {

    private int id;
    private String name;
    private List<String> tel;
    private List<String> address;
    private List<String> eventDate;
    private Bitmap profile;
    private boolean choiceTel;

    public TelData(int id, String name, List<String> tel, List<String> address, List<String> eventDate, Bitmap profile, boolean choiceTel){
        this.id = id;
        this.name = name;
        this.tel = tel;
        this.address = address;
        this.eventDate = eventDate;
        this.profile = profile;
        this.choiceTel = choiceTel;
    }

    public TelData(TelData telData){
        this.id = telData.getId();
        this.name = telData.getName();
        this.tel = telData.getTel();
        this.profile = telData.getProfile();
        this.choiceTel = telData.isChoiceTel();
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

    public List<String> getAddress() {
        return address;
    }

    public void setAddress(List<String> address) {
        this.address = address;
    }

    public List<String> getEventDate() {
        return eventDate;
    }

    public void setEventDate(List<String> eventDate) {
        this.eventDate = eventDate;
    }

    public Bitmap getProfile() {
        return profile;
    }

    public void setProfile(Bitmap profile) {
        this.profile = profile;
    }

    public boolean isChoiceTel() {
        return choiceTel;
    }

    public void setChoiceTel(boolean choiceTel) {
        this.choiceTel = choiceTel;
    }
}
