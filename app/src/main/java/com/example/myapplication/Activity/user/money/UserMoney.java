package com.example.myapplication.Activity.user.money;

import java.time.LocalDate;
import java.util.Date;

public class UserMoney {

    /*
        1. 금액
        2. 추가,차감
        3. 날짜
     */

    private int money;
    private String moneyName;
    private LocalDate moneyDt;

    public UserMoney(int _money, String _moneyName, LocalDate moneyDt){
        this.money = _money;
        this.moneyName = _moneyName;
        this.moneyDt = moneyDt;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public String getMoneyName() {
        return moneyName;
    }

    public void setMoneyName(String moneyFg) {
        this.moneyName = moneyFg;
    }

    public LocalDate getMoneyDt() {
        return moneyDt;
    }

    public void setMoneyDt(LocalDate moneyDt) {
        this.moneyDt = moneyDt;
    }
}
