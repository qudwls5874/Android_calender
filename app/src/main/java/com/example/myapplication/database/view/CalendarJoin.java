package com.example.myapplication.database.view;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.myapplication.database.table.menu.MenuCategory;
import com.example.myapplication.database.table.schedule.ScheduleCalendarD;
import com.example.myapplication.database.table.schedule.ScheduleCalendarH;

public class CalendarJoin {

    @Embedded
    public ScheduleCalendarH scheduleCalendarH;

    @Relation(parentColumn = "menuCategoryId", entityColumn = "menuCategoryId")
    public MenuCategory menuCategory;

    @Relation(parentColumn = "calHId", entityColumn = "calHId")
    public ScheduleCalendarD scheduleCalendarD;


}
