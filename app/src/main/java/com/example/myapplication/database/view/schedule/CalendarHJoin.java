package com.example.myapplication.database.view.schedule;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.myapplication.database.table.menu.MenuCategory;
import com.example.myapplication.database.table.schedule.ScheduleCalendarD;
import com.example.myapplication.database.table.schedule.ScheduleCalendarH;

import java.util.List;

public class CalendarHJoin {

    @Embedded
    public ScheduleCalendarH scheduleCalendarH;

    @Relation(entity = MenuCategory.class, parentColumn = "menuCategoryId", entityColumn = "menuCategoryId")
    public List<MenuCategory> menuCategory;

    @Relation(entity = ScheduleCalendarD.class, parentColumn = "calHId", entityColumn = "calHId")
    public List<CalendarDJoin> calendarDJoins;

}
