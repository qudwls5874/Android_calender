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
import com.example.myapplication.database.dao.UserDao;
import com.example.myapplication.database.table.MenuCategory;
import com.example.myapplication.database.table.MoneyName;
import com.example.myapplication.database.table.User;

import java.util.concurrent.Executors;


@Database(entities = {User.class, MoneyName.class, MenuCategory.class}, version = 1, exportSchema = false)
public abstract class UserDatabase extends RoomDatabase {

    public abstract UserDao getUserDao();
    public abstract MoneyNameDao getMoneyNameDao();
    public abstract MenuCategoryDao getMenuCategoryDao();

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
        menuCategoryDao.insert(new MenuCategory("네일아트"));
        menuCategoryDao.insert(new MenuCategory("속눈썹"));
        menuCategoryDao.insert(new MenuCategory("헤어"));
        menuCategoryDao.insert(new MenuCategory("애견미용"));
        menuCategoryDao.insert(new MenuCategory("미용"));
        menuCategoryDao.insert(new MenuCategory("미용"));
        menuCategoryDao.insert(new MenuCategory("미용"));

    }


}




