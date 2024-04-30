package com.example.myapplication.database.table;

import androidx.room.Entity;

@Entity
public class UserCash {

    private String ucDt;
    private int userId;
    private int ucPayment;
    private int ucFg; // 0:금액추가, 1:외상값음.

    public UserCash(String ucDt, int ucPayment, int ucFg) {
        this.ucDt = ucDt;
        this.ucPayment = ucPayment;
        this.ucFg = ucFg;
    }

    public String getUcDt() {
        return ucDt;
    }

    public void setUcDt(String ucDt) {
        this.ucDt = ucDt;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getUcPayment() {
        return ucPayment;
    }

    public void setUcPayment(int ucPayment) {
        this.ucPayment = ucPayment;
    }

    public int getUcFg() {
        return ucFg;
    }

    public void setUcFg(int ucFg) {
        this.ucFg = ucFg;
    }
}
