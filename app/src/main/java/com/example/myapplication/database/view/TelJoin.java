package com.example.myapplication.database.view;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.myapplication.database.table.MenuCategory;
import com.example.myapplication.database.table.MenuList;
import com.example.myapplication.database.table.User;
import com.example.myapplication.database.table.user.UserTel;

public class TelJoin {

    @Embedded
    public UserTel userTel;

    @Relation(parentColumn = "userID", entityColumn = "userId")
    public User user;

    @Relation(parentColumn = "menuCategoryId", entityColumn = "menuCategoryId")
    public MenuCategory menuCategory;

    @Relation(parentColumn = "menuListId", entityColumn = "menuListId")
    public MenuList menuList;


}
