package com.example.myapplication.database.table.userbag;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "usercoupons")
public class UserCoupon {

    @PrimaryKey(autoGenerate = true)
    private Long couponId;         // 쿠폰 아이디
    private Long userId;           // 유저 인덱스
    private long menuCategoryId; // 메뉴 헤더 인덱스
    private long menuListId;     // 메뉴 상세 인덱스
    private String couponInsertDt; // 발급 일자
    private String couponDeleteDt; // 사용 일자
    private int couponPayment;     // 할인 금액
    private int couponFg;          // 0:빼기, 1:퍼센트.

    public UserCoupon(String couponInsertDt, String couponDeleteDt, int couponPayment, int couponFg) {
        this.couponInsertDt = couponInsertDt;
        this.couponDeleteDt = couponDeleteDt;
        this.couponPayment = couponPayment;
        this.couponFg = couponFg;
    }

    public Long getCouponId() {
        return couponId;
    }

    public void setCouponId(Long couponId) {
        this.couponId = couponId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
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

    public String getCouponInsertDt() {
        return couponInsertDt;
    }

    public void setCouponInsertDt(String couponInsertDt) {
        this.couponInsertDt = couponInsertDt;
    }

    public String getCouponDeleteDt() {
        return couponDeleteDt;
    }

    public void setCouponDeleteDt(String couponDeleteDt) {
        this.couponDeleteDt = couponDeleteDt;
    }

    public int getCouponPayment() {
        return couponPayment;
    }

    public void setCouponPayment(int couponPayment) {
        this.couponPayment = couponPayment;
    }

    public int getCouponFg() {
        return couponFg;
    }

    public void setCouponFg(int couponFg) {
        this.couponFg = couponFg;
    }
}
