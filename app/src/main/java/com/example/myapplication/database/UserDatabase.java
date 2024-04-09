package com.example.myapplication.database;


import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.myapplication.database.dao.MoneyNameDao;
import com.example.myapplication.database.dao.UserDao;
import com.example.myapplication.database.table.MoneyName;
import com.example.myapplication.database.table.User;


@Database(entities = {User.class, MoneyName.class}, version = 4, exportSchema = false)
public abstract class UserDatabase extends RoomDatabase {
    public abstract UserDao getUserDao();
    public abstract MoneyNameDao getMoneyName();
}
