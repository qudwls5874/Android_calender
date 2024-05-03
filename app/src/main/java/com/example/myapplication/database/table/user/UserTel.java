package com.example.myapplication.database.table.user;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.example.myapplication.database.table.User;

@Entity(tableName = "usertels",
        foreignKeys = @ForeignKey(  entity = User.class,
                                    parentColumns = "userId",
                                    childColumns = "userId",
                                    onDelete = ForeignKey.CASCADE),
        primaryKeys = {"telId", "userId"})
public class UserTel {

    private long telId;          // 번호 인덱스
    private long userId;         // 유저 인덱스
    private String telName;     // 번호 타입
    private String telNumber;   // 번호

    public UserTel(String telName, String telNumber) {
        this.telName = telName;
        this.telNumber = telNumber;
    }

    public long getTelId() {
        return telId;
    }

    public void setTelId(long telId) {
        this.telId = telId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getTelName() {
        return telName;
    }

    public void setTelName(String telName) {
        this.telName = telName;
    }

    public String getTelNumber() {
        return telNumber;
    }

    public void setTelNumber(String telNumber) {
        this.telNumber = telNumber;
    }
}
