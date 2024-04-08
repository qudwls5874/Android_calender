package com.example.myapplication.database;

import androidx.room.Database;

import com.example.myapplication.database.dao.MoneyNameDao;
import com.example.myapplication.database.table.MoneyName;

@Database(entities = {MoneyName.class}, version = 1, exportSchema = false)
public class MoneyDatabase {
    public abstract MoneyNameDao getMoneyDao();
}


/*
* @Database(entities = {User.class}, version = 2, exportSchema = false)
public abstract class UserDatabase extends RoomDatabase {
    public abstract UserDao getUserDao();
}
*/
