package com.example.myapplication.database.table;

import androidx.room.Entity;

@Entity
public class UserCash {

    private String ucDt;
    private long userId;
    private long cashId;
    private int cashPayment;
    private int cashFg; // 0:금액추가, 1:외상값음.

    public UserCash(String ucDt, int cashPayment, int cashFg) {
        this.ucDt = ucDt;
        this.cashPayment = cashPayment;
        this.cashFg = cashFg;
    }

    public String getUcDt() {
        return ucDt;
    }

    public void setUcDt(String ucDt) {
        this.ucDt = ucDt;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getCashId() {
        return cashId;
    }

    public void setCashId(long cashId) {
        this.cashId = cashId;
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
