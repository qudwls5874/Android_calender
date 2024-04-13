package com.example.myapplication.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.myapplication.database.table.SettingDate;

import java.util.List;

@Dao
public interface SettingDateDao {

    @Insert
    void insert(SettingDate settingDate);

    @Update
    void update(SettingDate settingDate);

    @Update
    void updateAll(List<SettingDate> settingDateList);

    @Query("SELECT * FROM settingdates")
    List<SettingDate> getAllSelect();

}
