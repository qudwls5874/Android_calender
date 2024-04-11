package com.example.myapplication.database.table;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "menucategorys")
public class MenuCategory {

    @PrimaryKey(autoGenerate = true)
    int menuCategoryId;
    String menuCategoryName;

    public MenuCategory(String menuCategoryName){
        this.menuCategoryName = menuCategoryName;
    }

    public int getMenuCategoryId() {
        return menuCategoryId;
    }

    public void setMenuCategoryId(int menuCategoryId) {
        this.menuCategoryId = menuCategoryId;
    }

    public String getMenuCategoryName() {
        return menuCategoryName;
    }

    public void setMenuCategoryName(String menuCategoryName) {
        this.menuCategoryName = menuCategoryName;
    }
}
