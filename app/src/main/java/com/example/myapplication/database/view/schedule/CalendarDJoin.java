package com.example.myapplication.database.view.schedule;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.myapplication.database.table.menu.MenuList;
import com.example.myapplication.database.table.schedule.ScheduleCalendarD;

public class CalendarDJoin {

    @Embedded
    public ScheduleCalendarD scheduleCalendarD;

    @Relation(entity = MenuList.class, parentColumn = "menuListId", entityColumn = "menuListId")
    public MenuList menuList;

}
