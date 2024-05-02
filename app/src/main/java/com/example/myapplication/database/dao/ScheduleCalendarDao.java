package com.example.myapplication.database.dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.example.myapplication.database.table.ScheduleCalendar;
import com.example.myapplication.database.view.CalendarJoin;

import java.util.List;

@Dao
public interface ScheduleCalendarDao {

    @Query( "SELECT * " +
            "FROM ScheduleCalendar " +
            "WHERE userId = :userId " +
            "ORDER BY calDt DESC ")
    List<CalendarJoin> getScheduleCalendarlist(Long userId);

}
