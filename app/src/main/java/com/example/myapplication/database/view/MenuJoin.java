package com.example.myapplication.database.view;


import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.myapplication.database.table.MenuCategory;
import com.example.myapplication.database.table.MenuList;

import java.util.List;

public class MenuJoin {

    @Embedded
    public MenuCategory menuCategory;

    @Relation(parentColumn = "menuCategoryId", entityColumn = "menuCategoryId")
    public List<MenuList> menuLists;

}
