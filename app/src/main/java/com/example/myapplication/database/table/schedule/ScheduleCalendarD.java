package com.example.myapplication.database.table.schedule;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.example.myapplication.database.table.User;
import com.example.myapplication.database.table.menu.MenuList;

@Entity(tableName = "schedulecalendards",
        foreignKeys = { @ForeignKey(    entity = User.class,
                                        parentColumns = "userId",
                                        childColumns = "userId",
                                        onDelete = ForeignKey.CASCADE),
                        @ForeignKey(    entity = ScheduleCalendarH.class,
                                        parentColumns = "calHId",
                                        childColumns = "calHId"),
                        @ForeignKey(    entity = MenuList.class,
                                        parentColumns = "menuListId",
                                        childColumns = "menuListId")})
public class ScheduleCalendarD {

    @PrimaryKey(autoGenerate = true)
    private long calDId;          // 캘린더 인덱스
    private long calHId;          // 캘린더 인덱스
    private long userId;         // 유저 인덱스
    private long menuListId;     // 메뉴 상세 인덱스
    private String calTime;      // 시간
    private int calPayment;      // 결제금.
    private int calFg;           // 0 : 결제, 1 : 차감

    public ScheduleCalendarD(String calTime, int calPayment, int calFg) {
        this.calTime = calTime;
        this.calPayment = calPayment;
        this.calFg = calFg;
    }

    public long getCalDId() {
        return calDId;
    }

    public void setCalDId(long calDId) {
        this.calDId = calDId;
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

    public long getMenuListId() {
        return menuListId;
    }

    public void setMenuListId(long menuListId) {
        this.menuListId = menuListId;
    }

    public String getCalTime() {
        return calTime;
    }

    public void setCalTime(String calTime) {
        this.calTime = calTime;
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
