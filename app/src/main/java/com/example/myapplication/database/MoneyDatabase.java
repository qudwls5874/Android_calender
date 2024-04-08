package com.example.myapplication.database;

import androidx.room.Database;

import com.example.myapplication.database.dao.MoneyNameDao;
import com.example.myapplication.database.table.MoneyName;

@Database(entities = {MoneyName.class}, version = 1, exportSchema = false)
public abstract class MoneyDatabase {
    public abstract MoneyNameDao getMoneyDao();
}
