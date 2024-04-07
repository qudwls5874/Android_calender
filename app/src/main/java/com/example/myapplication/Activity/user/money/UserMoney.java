package com.example.myapplication.Activity.user.money;

import java.util.Date;

public class UserMoney {

    /*
        1. 금액
        2. 추가,차감
        3. 날짜
     */

    private int money;
    private int moneyFg;
    private Date moneyDt;

    public UserMoney(int _money, int _moneyFg, Date moneyDt){
        money = _money;
        moneyFg = _moneyFg;
        moneyDt =moneyDt;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public int getMoneyFg() {
        return moneyFg;
    }

    public void setMoneyFg(int moneyFg) {
        this.moneyFg = moneyFg;
    }

    public Date getMoneyDt() {
        return moneyDt;
    }

    public void setMoneyDt(Date moneyDt) {
        this.moneyDt = moneyDt;
    }
}
