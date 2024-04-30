package com.example.myapplication.database.table;

import androidx.room.Entity;

@Entity
public class ServiceCalender {


    private String scDt;        // 날짜
    private int userId;         // 유저 인덱스
    private int menuCategoryId; // 메뉴 헤더 인덱스
    private int menuListId;     // 메뉴 상세 인덱스
    private int scPayment;      // 결제금.
    private int scFg;           // 0 : 결제, 1 : 차감

    public ServiceCalender(String scDt, int scPayment, int scFg) {
        this.scDt = scDt;
        this.scPayment = scPayment;
        this.scFg = scFg;
    }

    public String getScDt() {
        return scDt;
    }

    public void setScDt(String scDt) {
        this.scDt = scDt;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getMenuCategoryId() {
        return menuCategoryId;
    }

    public void setMenuCategoryId(int menuCategoryId) {
        this.menuCategoryId = menuCategoryId;
    }

    public int getMenuListId() {
        return menuListId;
    }

    public void setMenuListId(int menuListId) {
        this.menuListId = menuListId;
    }

    public int getScPayment() {
        return scPayment;
    }

    public void setScPayment(int scPayment) {
        this.scPayment = scPayment;
    }

    public int getScFg() {
        return scFg;
    }

    public void setScFg(int scFg) {
        this.scFg = scFg;
    }
}
