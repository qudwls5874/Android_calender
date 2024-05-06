package com.example.myapplication.database.view;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.myapplication.database.table.menu.MenuCategory;
import com.example.myapplication.database.table.menu.MenuList;
import com.example.myapplication.database.table.userbag.UserCash;

public class CashJoin {


    @Embedded
    public UserCash userCash;

    @Relation(parentColumn = "menuCategoryId", entityColumn = "menuCategoryId")
    public MenuCategory menuCategory;

    @Relation(parentColumn = "menuListId", entityColumn = "menuListId")
    public MenuList menuList;


}
