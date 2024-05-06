package com.example.myapplication.database.table.schedule;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.example.myapplication.database.table.User;
import com.example.myapplication.database.table.menu.MenuCategory;

@Entity(tableName = "schedulecalendarhs",
        foreignKeys = { @ForeignKey(    entity = User.class,
                                        parentColumns = "userId",
                                        childColumns = "userId",
                                        onDelete = ForeignKey.CASCADE),
                        @ForeignKey(    entity = MenuCategory.class,
                                        parentColumns = "menuCategoryId",
                                        childColumns = "menuCategoryId")})
public class ScheduleCalendarH {

    @PrimaryKey(autoGenerate = true)
    private long calHId;          // 캘린더 인덱스
    private long userId;         // 유저 인덱스
    private long menuCategoryId; // 메뉴 헤더 인덱스
    private String calDt;        // 날짜

    // 생성자
    public ScheduleCalendarH(String calDt) {
        this.calDt = calDt;
    }

    public long getCalHId() {
        return calHId;
    }

    public void setCalHId(long calHId) {
        this.calHId = calHId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getMenuCategoryId() {
        return menuCategoryId;
    }

    public void setMenuCategoryId(long menuCategoryId) {
        this.menuCategoryId = menuCategoryId;
    }

    public String getCalDt() {
        return calDt;
    }

    public void setCalDt(String calDt) {
        this.calDt = calDt;
    }
}
