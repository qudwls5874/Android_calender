package com.example.myapplication.database.view;


import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.myapplication.database.table.MenuCategory;
import com.example.myapplication.database.table.MenuList;
import com.example.myapplication.database.table.ScheduleCalendar;

public class CalendarJoin {

    @Embedded
    public ScheduleCalendar scheduleCalendar;

    @Relation(parentColumn = "menuCategoryId", entityColumn = "menuCategoryId")
    public MenuCategory menuCategory;

    @Relation(parentColumn = "menuListId", entityColumn = "menuListId")
    public MenuList menuList;


}
