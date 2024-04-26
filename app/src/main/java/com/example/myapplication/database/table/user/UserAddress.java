package com.example.myapplication.database.table.user;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "useraddresss")
public class UserAddress {

    @PrimaryKey(autoGenerate = true)
    private int addressId;
    private int userId;
    private String addressType;
    private String addressName;

    public UserAddress(int addressId, String addressType, String addressName) {
        this.addressId = addressId;
        this.addressType = addressType;
        this.addressName = addressName;
    }

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getAddressType() {
        return addressType;
    }

    public void setAddressType(String addressType) {
        this.addressType = addressType;
    }

    public String getAddressName() {
        return addressName;
    }

    public void setAddressName(String addressName) {
        this.addressName = addressName;
    }
}
