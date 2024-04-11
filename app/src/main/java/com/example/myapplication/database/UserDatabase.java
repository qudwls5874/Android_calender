package com.example.myapplication.database;


import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.myapplication.database.dao.MenuCategoryDao;
import com.example.myapplication.database.dao.MoneyNameDao;
import com.example.myapplication.database.dao.SettingDateDao;
import com.example.myapplication.database.dao.UserDao;
import com.example.myapplication.database.table.MenuCategory;
import com.example.myapplication.database.table.MoneyName;
import com.example.myapplication.database.table.SettingDate;
import com.example.myapplication.database.table.User;

import java.util.concurrent.Executors;


@Database(entities =
        {
                User.class, MoneyName.class, MenuCategory.class,
                SettingDate.class
        }, version = 1, exportSchema = false )

public abstract class UserDatabase extends RoomDatabase {

    public abstract UserDao getUserDao();
    public abstract MoneyNameDao getMoneyNameDao();
    public abstract MenuCategoryDao getMenuCategoryDao();
    public abstract SettingDateDao getSettingDao();

    private static UserDatabase instance;
    public static synchronized UserDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),UserDatabase.class, "userDatabase")
                    .fallbackToDestructiveMigration()
                    .addCallback(new RoomDatabase.Callback(){
                        @Override
                        public void onCreate(@NonNull SupportSQLiteDatabase db) {
                            super.onCreate(db);
                            Executors.newSingleThreadExecutor().execute(() -> {
                                insertInitialData(instance);
                            });
                        }
                    })
                    .build();
        }
        return instance;
    }

    private static void insertInitialData(UserDatabase  userDatabase) {
        MoneyNameDao moneyNameDao = userDatabase.getMoneyNameDao();
        moneyNameDao.insert(new MoneyName("정액제"));
        moneyNameDao.insert(new MoneyName("차감"));
        moneyNameDao.insert(new MoneyName("결제"));

        MenuCategoryDao menuCategoryDao = userDatabase.getMenuCategoryDao();
        menuCategoryDao.insert(new MenuCategory("네일"));
        menuCategoryDao.insert(new MenuCategory("헤어"));
        menuCategoryDao.insert(new MenuCategory("애견미용"));
        menuCategoryDao.insert(new MenuCategory("속눈썹"));
        menuCategoryDao.insert(new MenuCategory("피부"));
        menuCategoryDao.insert(new MenuCategory("메이크업"));
        menuCategoryDao.insert(new MenuCategory("왁싱"));
        menuCategoryDao.insert(new MenuCategory("타투"));
        menuCategoryDao.insert(new MenuCategory("미용문신"));

        SettingDateDao settingDateDao = userDatabase.getSettingDao();
        settingDateDao.insert(new SettingDate(1100));
        settingDateDao.insert(new SettingDate(2100));
        settingDateDao.insert(new SettingDate(30));
        settingDateDao.insert(new SettingDate(0));
/*
* 네일
헤어
애견미용
속눈썹
피부
메이크업
왁싱
아트타투, 헤나타투
미용문신(반영구)

바버
마사지
드라이
태닝
눈썹
실제모
*/
    }


}




