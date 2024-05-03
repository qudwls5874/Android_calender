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
    private String addressName;
    private String addressContent;

    public UserAddress(String addressName, String addressContent) {
        this.addressName = addressName;
        this.addressContent =addressContent;
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

    public String getAddressName() {
        return addressName;
    }

    public void setAddressName(String addressName) {
        this.addressName = addressName;
    }

    public String getAddressContent() {
        return addressContent;
    }

    public void setAddressContent(String addressContent) {
        this.addressContent = addressContent;
    }
}
