package com.example.myapplication.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.myapplication.database.table.menu.MenuList;
import com.example.myapplication.database.view.MenuJoin;

import java.util.List;

@Dao
public interface MenuListDao {

    @Insert
    void insert(MenuList menuList);

    @Update
    void update(MenuList menuList);

    @Delete
    void deleteChoice(List<MenuList> menuList);

    @Query("SELECT * FROM menulists")
    List<MenuList> selectAll();
/*
    @Query("SELECT  * " +
            "FROM   menulists as A " +
            "LEFT JOIN menucategorys as B " +
            "ON   A.menuCategoryId = B.menuCategoryId " +
            "WHERE B.menuCategoryYn = \"Y\" " +
            "ORDER BY B.menuCategoryId, A.menuListId ")
    List<MenuJoin> getMenuJoinList();
*/

    @Query("SELECT * FROM menucategorys WHERE menuCategoryYn = 'Y' AND menuCategoryFg = :fg")
    List<MenuJoin> getMenuJoinList(int fg);

}
