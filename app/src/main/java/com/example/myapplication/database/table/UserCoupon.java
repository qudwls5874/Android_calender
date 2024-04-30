package com.example.myapplication.database.table;

import androidx.room.Entity;

@Entity
public class UserCoupon {


    private int userId; // 테이블 인덱스
    private int cpId;   // 쿠폰 아이디
    private int cpPayment;
    private int cpFg; // 0:금액추가, 1:외상값음.


}
