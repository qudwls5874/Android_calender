package com.example.myapplication.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.myapplication.database.table.MoneyName;

import java.util.List;

@Dao
public interface MoneyNameDao {

    @Insert
    void insert(MoneyName moneyName);

    @Update
    void update(MoneyName moneyName);

    @Delete
    void delete(MoneyName moneyName);

    @Query("SELECT * FROM moneynames")
    List<MoneyName> getAllMoney();



}
