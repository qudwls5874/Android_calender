package com.example.myapplication.database.table;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "users")
public class User {

    @PrimaryKey(autoGenerate = true)
    private long userId;         // 테이블 인덱스
    private String userUrl;     // 프로필 경로, 주소록 id
    private String name;        // 이름
    private String userTel;     // 대표 번호
    private String userAddress; // 대표 주소
    private String userEvent;   // 대표 일정
    private String money;       // 금액
    private int userFg;         // 0 : 회원, 1 : 비회원


    public User(String userUrl, String name, String userTel, String userAddress, String userEvent, String money, int userFg) {
        this.userUrl = userUrl;
        this.name = name;
        this.userTel = userTel;
        this.userAddress = userAddress;
        this.userEvent = userEvent;
        this.money = money;
        this.userFg = userFg;
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

    public String getUserTel() {
        return userTel;
    }

    public void setUserTel(String userTel) {
        this.userTel = userTel;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public String getUserEvent() {
        return userEvent;
    }

    public void setUserEvent(String userEvent) {
        this.userEvent = userEvent;
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
