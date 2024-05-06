package com.example.myapplication.database.view;


import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.myapplication.database.table.ScheduleCalendar;
import com.example.myapplication.database.table.User;
import com.example.myapplication.database.table.schedule.ScheduleCalendarH;
import com.example.myapplication.database.table.user.UserAddress;
import com.example.myapplication.database.table.user.UserEvent;
import com.example.myapplication.database.table.user.UserTel;
import com.example.myapplication.database.table.userbag.UserCash;
import com.example.myapplication.database.table.userbag.UserCoupon;
import com.example.myapplication.database.view.schedule.CalendarHJoin;

import java.util.List;

public class UserJoin {

    @Embedded
    public User user;

    @Relation(parentColumn = "userId", entityColumn = "userId")
    public List<UserTel> userTelList;

    @Relation(parentColumn = "userId", entityColumn = "userId")
    public List<UserAddress> userAddressList;

    @Relation(parentColumn = "userId", entityColumn = "userId")
    public List<UserEvent> userEventsList;

    @Relation(entity = ScheduleCalendarH.class, parentColumn = "userId", entityColumn = "userId")
    public List<CalendarHJoin> calendarHJoins;

    @Relation(entity = UserCash.class, parentColumn = "userId", entityColumn = "userId")
    public List<CashJoin> userCashes;

    @Relation(entity = UserCoupon.class, parentColumn = "userId", entityColumn = "userId")
    public List<CouponJoin> userCoupons;

}
