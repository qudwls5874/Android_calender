package com.example.myapplication.database.table;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "menulists")
public class MenuList {

    @PrimaryKey(autoGenerate = true)
    int menuListId;
    int menuCategoryId;
    String menuName;
    String menuMoney;

    public MenuList(int menuCategoryId, String menuName, String menuMoney){
        this.menuCategoryId = menuCategoryId;
        this.menuName = menuName;
        this.menuMoney = menuMoney;
    }

    public int getMenuListId() {
        return menuListId;
    }

    public void setMenuListId(int menuListId) {
        this.menuListId = menuListId;
    }

    public int getMenuCategoryId() {
        return menuCategoryId;
    }

    public void setMenuCategoryId(int menuCategoryId) {
        this.menuCategoryId = menuCategoryId;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getMenuMoney() {
        return menuMoney;
    }

    public void setMenuMoney(String menuMoney) {
        this.menuMoney = menuMoney;
    }
}
