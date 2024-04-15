package com.example.myapplication.database.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.myapplication.database.repository.MenuCategoryRepository;
import com.example.myapplication.database.table.MenuCategory;

import java.util.List;

public class MenuCategoryViewModel extends AndroidViewModel {

    private MenuCategoryRepository repository;
    private LiveData<List<MenuCategory>> resultList;

    public MenuCategoryViewModel(@NonNull Application application) {
        super(application);
        repository = new MenuCategoryRepository(application);
        resultList = repository.list;
//        for (int i = 0; i < list.getValue().size(); i++){
//            Log.i("카테고리 데이터", list.getValue().get(i).getMenuCategoryName());
//        }
    }

    public LiveData<List<MenuCategory>> getList() {
        return resultList;
    }
}
