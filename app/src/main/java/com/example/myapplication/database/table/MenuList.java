package com.example.myapplication.database.table;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "menulists")
public class MenuList {

    @PrimaryKey(autoGenerate = true)
    int menuListId;
    int menuCategoryId;
    String menuName;
    int menuMoney;

    public MenuList(){

    }

    public MenuList(int menuCategoryId, String menuName, int menuMoney){
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

    public int getMenuMoney() {
        return menuMoney;
    }

    public void setMenuMoney(int menuMoney) {
        this.menuMoney = menuMoney;
    }
}
