package com.example.myapplication.database.table.user;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.example.myapplication.database.table.User;

@Entity(tableName = "useraddresss",
        foreignKeys = @ForeignKey(  entity = User.class,
                                    parentColumns = "userId",
                                    childColumns = "userId",
                                    onDelete = ForeignKey.CASCADE),
        primaryKeys = {"addressId", "userId"})
public class UserAddress {

    private long addressId;
    private long userId;
    private String addressType;
    private String addressName;

    public UserAddress(String addressType, String addressName) {
        this.addressType = addressType;
        this.addressName = addressName;
    }

    public long getAddressId() {
        return addressId;
    }

    public void setAddressId(long addressId) {
        this.addressId = addressId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
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
