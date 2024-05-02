package com.example.myapplication.database.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.myapplication.database.repository.ScheduleCalendarRepository;
import com.example.myapplication.database.view.CalendarJoin;

import java.util.List;

public class ScheduleCalendarViewModel extends AndroidViewModel {

    private MutableLiveData<List<CalendarJoin>> list = new MutableLiveData<>();
    private ScheduleCalendarRepository repository;

    public ScheduleCalendarViewModel(@NonNull Application application) {
        super(application);
        repository = new ScheduleCalendarRepository(application);
    }

    public LiveData<List<CalendarJoin>> getLiveList(){
        return list;
    }

    public void getScheduleCalendar(Long userId){
        list.setValue(repository.selectScheduleCalendar(userId));
    }



}
