package com.example.myapplication.database.table.userbag;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "usercashs")
public class UserCash {

    @PrimaryKey(autoGenerate = true)
    private Long cashId;  // 케시 아이디
    private Long userId;     // 유저 아이디
    private long menuCategoryId; // 메뉴 헤더 인덱스
    private long menuListId;     // 메뉴 상세 인덱스
    private String cashDt;  // 적립일
    private int cashPayment;// 적립금
    private int cashFg;     // 0:금액추가, 1:외상값음.

    public UserCash(String cashDt, int cashPayment, int cashFg) {
        this.cashDt = cashDt;
        this.cashPayment = cashPayment;
        this.cashFg = cashFg;
    }

    public Long getCashId() {
        return cashId;
    }

    public void setCashId(Long cashId) {
        this.cashId = cashId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public long getMenuCategoryId() {
        return menuCategoryId;
    }

    public void setMenuCategoryId(long menuCategoryId) {
        this.menuCategoryId = menuCategoryId;
    }

    public long getMenuListId() {
        return menuListId;
    }

    public void setMenuListId(long menuListId) {
        this.menuListId = menuListId;
    }

    public String getCashDt() {
        return cashDt;
    }

    public void setCashDt(String cashDt) {
        this.cashDt = cashDt;
    }

    public int getCashPayment() {
        return cashPayment;
    }

    public void setCashPayment(int cashPayment) {
        this.cashPayment = cashPayment;
    }

    public int getCashFg() {
        return cashFg;
    }

    public void setCashFg(int cashFg) {
        this.cashFg = cashFg;
    }
}
