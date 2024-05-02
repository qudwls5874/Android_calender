package com.example.myapplication.database.table;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "users")
public class User {

    @PrimaryKey(autoGenerate = true)
    private long userId;         // 테이블 인덱스
    private String name;        // 이름
    private String userUrl;     // 프로필 경로, 주소록 id
    private String money;       // 금액
    private int userFg;         // 0 : 회원, 1 : 비회원

    public User(String name, String userUrl, String money){
        this.name = name;
        this.userUrl = userUrl;
        this.money = money;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
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

    public int getUserFg() {
        return userFg;
    }

    public void setUserFg(int userFg) {
        this.userFg = userFg;
    }
}
