package com.example.myapplication.database.table.user;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "usertels")
public class UserTel {

    @PrimaryKey(autoGenerate = true)
    private int telId;
    private int userId;
    private String telType;
    private String telNumber;

    public UserTel(int telId, String telType, String telNumber) {
        this.telId = telId;
        this.telType = telType;
        this.telNumber = telNumber;
    }

    public int getTelId() {
        return telId;
    }

    public void setTelId(int telId) {
        this.telId = telId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getTelType() {
        return telType;
    }

    public void setTelType(String telType) {
        this.telType = telType;
    }

    public String getTelNumber() {
        return telNumber;
    }

    public void setTelNumber(String telNumber) {
        this.telNumber = telNumber;
    }
}
