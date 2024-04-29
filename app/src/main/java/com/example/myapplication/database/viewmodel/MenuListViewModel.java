package com.example.myapplication.database.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.myapplication.database.repository.MenuListRepository;
import com.example.myapplication.database.table.MenuList;
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

    public LiveData<List<MenuJoin>> getList(){
        return list;
    }
    public void getSelectList(int categoryFg){
        repository.selectList(categoryFg);
    }

    public void setList(MenuList menuList, int categoryFg){
        repository.insertList(menuList, categoryFg);
    }
    public void setDeleteList(List<MenuList> menuLists, int categoryFg){repository.delete(menuLists, categoryFg);}
    public void setUpdate(MenuList menuList, int categoryFg) { repository.update(menuList, categoryFg);}



}
