package com.example.myapplication.database.table.menu;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "menucategorys")
public class MenuCategory {

    @PrimaryKey(autoGenerate = true)
    private long menuCategoryId;         // 메뉴 카테고리 헤더
    private String menuCategoryName;    // 메뉴 카테고리 명
    private String menuCategoryYn;      // 메뉴 카테고리 사용여부
    private int menuCategoryFg;         // 메뉴 카테고리 구분값 0:서비스, 1:정액권, 2:할인권

    public MenuCategory() {
    }

    public MenuCategory(String menuCategoryName, String menuCategoryYn, int menuCategoryFg){
        this.menuCategoryName = menuCategoryName;
        this.menuCategoryYn = menuCategoryYn;
        this.menuCategoryFg = menuCategoryFg;
    }

    public MenuCategory(MenuCategory menuCategory){
        this.menuCategoryId = menuCategory.getMenuCategoryId();
        this.menuCategoryName = menuCategory.getMenuCategoryName();
        this.menuCategoryYn = menuCategory.getMenuCategoryYn();
        this.menuCategoryFg = menuCategory.getMenuCategoryFg();
    }


    public long getMenuCategoryId() {
        return menuCategoryId;
    }

    public void setMenuCategoryId(long menuCategoryId) {
        this.menuCategoryId = menuCategoryId;
    }

    public String getMenuCategoryName() {
        return menuCategoryName;
    }

    public void setMenuCategoryName(String menuCategoryName) {
        this.menuCategoryName = menuCategoryName;
    }

    public String getMenuCategoryYn() {
        return menuCategoryYn;
    }

    public void setMenuCategoryYn(String menuCategoryYn) {
        this.menuCategoryYn = menuCategoryYn;
    }

    public int getMenuCategoryFg() {
        return menuCategoryFg;
    }

    public void setMenuCategoryFg(int menuCategoryFg) {
        this.menuCategoryFg = menuCategoryFg;
    }
}
