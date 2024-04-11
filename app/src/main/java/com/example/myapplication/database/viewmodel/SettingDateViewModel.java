package com.example.myapplication.database.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.myapplication.database.repository.SettingDateRepository;
import com.example.myapplication.database.table.SettingDate;

import java.util.List;

public class SettingDateViewModel extends AndroidViewModel {

    private SettingDateRepository repository;
    private LiveData<List<SettingDate>> list;

    public SettingDateViewModel(@NonNull Application application) {
        super(application);
        repository = new SettingDateRepository(application);
        list = repository.settingDateList;
    }

    public LiveData<List<SettingDate>> getList(){
        return list;
    }
}
