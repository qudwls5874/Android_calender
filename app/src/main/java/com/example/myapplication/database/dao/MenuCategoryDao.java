package com.example.myapplication.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.myapplication.database.table.menu.MenuCategory;

import java.util.List;

@Dao
public interface MenuCategoryDao {

    @Insert
    void insert(MenuCategory menuCategory);

    @Update
    void updateAll(List<MenuCategory> menuCategory);

    @Delete
    void delete(MenuCategory menuCategory);

    @Query("SELECT * FROM menucategorys WHERE menuCategoryFg = '0' ")
    List<MenuCategory> getAllSelect();

}
