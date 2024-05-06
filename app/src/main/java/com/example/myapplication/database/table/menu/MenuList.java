package com.example.myapplication.database.table.menu;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "menulists")
public class MenuList {

    @PrimaryKey(autoGenerate = true)
    private long menuListId;         // 메뉴 상세 인덱스
    private long menuCategoryId;     // 메뉴 헤더 인덱스
    private String menuName;        // 메뉴명
    private int menuMoney;          // 금액

    public MenuList(){}

    // 기본.
    public MenuList(long menuCategoryId, String menuName, int menuMoney){
        this.menuCategoryId = menuCategoryId;
        this.menuName = menuName;
        this.menuMoney = menuMoney;
    }

    // 업데이트시 menuListId 값 필요
    public MenuList(long menuListId, long menuCategoryId, String menuName, int menuMoney){
        this.menuListId = menuListId;
        this.menuCategoryId = menuCategoryId;
        this.menuName = menuName;
        this.menuMoney = menuMoney;
    }

    public long getMenuListId() {
        return menuListId;
    }

    public void setMenuListId(long menuListId) {
        this.menuListId = menuListId;
    }

    public long getMenuCategoryId() {
        return menuCategoryId;
    }

    public void setMenuCategoryId(long menuCategoryId) {
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
