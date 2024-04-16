package com.example.myapplication.database.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.myapplication.database.repository.MenuListRepository;
import com.example.myapplication.database.view.MenuJoin;

import java.util.List;

public class MenuListViewModel extends AndroidViewModel {

    private MenuListRepository repository;
    private LiveData<List<MenuJoin>> list;

    public MenuListViewModel(@NonNull Application application) {
        super(application);
        repository  = new MenuListRepository(application);
        list = repository.joinList;
    }

    public LiveData<List<MenuJoin>> getList(){return list;}



}
