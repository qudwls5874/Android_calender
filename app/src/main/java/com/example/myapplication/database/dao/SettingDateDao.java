package com.example.myapplication.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.myapplication.database.table.SettingDate;

import java.util.List;

@Dao
public interface SettingDateDao {

    @Insert
    void insert(SettingDate settingDate);

    @Query("SELECT * FROM settingdates")
    List<SettingDate> getAllSelect();

}
