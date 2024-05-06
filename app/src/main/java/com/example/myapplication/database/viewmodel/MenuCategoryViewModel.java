package com.example.myapplication.database.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.myapplication.database.repository.MenuCategoryRepository;
import com.example.myapplication.database.table.menu.MenuCategory;

import java.util.List;

public class MenuCategoryViewModel extends AndroidViewModel {

    private MenuCategoryRepository repository;
    private LiveData<List<MenuCategory>> resultList;

    public MenuCategoryViewModel(@NonNull Application application) {
        super(application);
        repository = new MenuCategoryRepository(application);
        resultList = repository.list;
    }

    public LiveData<List<MenuCategory>> getList() {
        return resultList;
    }

    public void setList(List<MenuCategory> list){repository.updateList(list);}
}
