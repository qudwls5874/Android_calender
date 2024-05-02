package com.example.myapplication.database.table;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "schedulecalendar")
public class ScheduleCalendar {

    @PrimaryKey(autoGenerate = true)
    private long userId;         // 유저 인덱스
    private long menuCategoryId; // 메뉴 헤더 인덱스
    private long menuListId;     // 메뉴 상세 인덱스
    private long calId;          // 캘린더 인덱스
    private String calDt;        // 날짜
    private int calPayment;      // 결제금.
    private int calFg;           // 0 : 결제, 1 : 차감

    public ScheduleCalendar(String calDt, int calPayment, int calFg) {
        this.calDt = calDt;
        this.calPayment = calPayment;
        this.calFg = calFg;
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

    public long getMenuListId() {
        return menuListId;
    }

    public void setMenuListId(long menuListId) {
        this.menuListId = menuListId;
    }

    public long getCalId() {
        return calId;
    }

    public void setCalId(long calId) {
        this.calId = calId;
    }

    public String getCalDt() {
        return calDt;
    }

    public void setCalDt(String calDt) {
        this.calDt = calDt;
    }

    public int getCalPayment() {
        return calPayment;
    }

    public void setCalPayment(int calPayment) {
        this.calPayment = calPayment;
    }

    public int getCalFg() {
        return calFg;
    }

    public void setCalFg(int calFg) {
        this.calFg = calFg;
    }
}
