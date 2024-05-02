package com.example.myapplication.database.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.MutableLiveData;

import com.example.myapplication.database.UserDatabase;
import com.example.myapplication.database.dao.ScheduleCalendarDao;
import com.example.myapplication.database.table.ScheduleCalendar;
import com.example.myapplication.database.view.CalendarJoin;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class ScheduleCalendarRepository {

    public MutableLiveData<List<CalendarJoin>> list = new MutableLiveData<>();
    private ScheduleCalendarDao dao;

    public ScheduleCalendarRepository(Application application) {
        UserDatabase db = UserDatabase.getInstance(application);
        dao = db.getScheduleCalendarDao();
    }


    public List<CalendarJoin> selectScheduleCalendar(Long userId){
        try {
            return new SelectScheduleCalendarAsyncTask().execute(userId).get();
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    private class SelectScheduleCalendarAsyncTask extends AsyncTask<Long, Void, List<CalendarJoin>>{
        @Override
        protected List<CalendarJoin> doInBackground(Long... longs) {
            return dao.getScheduleCalendarlist(longs[0]);
        }
    }




}
