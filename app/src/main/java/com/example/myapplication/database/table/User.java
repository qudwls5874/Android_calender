package com.example.myapplication.database.table;

import android.graphics.Bitmap;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "users")
public class User {

    @PrimaryKey(autoGenerate = true)
    private int userId;         // 테이블 인덱스
    private String name;        // 이름
    private String userUrl;     // 프로필 경로, 주소록 id
    private String money;       // 금액

    public User(String name, String userUrl, String money){
        this.name = name;
        this.userUrl = userUrl;
        this.money = money;
    }

    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getUserUrl() {
        return userUrl;
    }

    public void setUserUrl(String userUrl) {
        this.userUrl = userUrl;
    }

    public String getMoney() {
        return money;
    }
    public void setMoney(String money) {
        this.money = money;
    }


}
