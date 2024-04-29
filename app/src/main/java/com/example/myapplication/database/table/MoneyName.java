package com.example.myapplication.database.table;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "moneynames")
public class MoneyName {

    @PrimaryKey(autoGenerate = true)
    private int moneyId;
    private String moneyName;

    public MoneyName(String moneyName){
        this.moneyName = moneyName;
    }

    public int getMoneyId() {
        return moneyId;
    }

    public void setMoneyId(int moneyId) {
        this.moneyId = moneyId;
    }

    public String getMoneyName() {
        return moneyName;
    }

    public void setMoneyName(String moneyName) {
        this.moneyName = moneyName;
    }
}
