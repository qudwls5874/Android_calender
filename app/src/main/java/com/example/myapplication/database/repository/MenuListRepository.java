package com.example.myapplication.database.repository;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.myapplication.database.UserDatabase;
import com.example.myapplication.database.dao.MenuListDao;
import com.example.myapplication.database.table.User;
import com.example.myapplication.database.view.MenuJoin;

import java.util.List;

public class MenuListRepository {

    public MutableLiveData<List<MenuJoin>> joinList = new MutableLiveData<>();
    private MenuListDao dao;
    private UserDatabase database;

    public MenuListRepository(Application application){
        database = UserDatabase.getInstance(application);
        dao = database.getMenuListDao();
        selectList();
    }


    public void selectList(){new SelectAllAsyncTask().execute();}
    public class SelectAllAsyncTask extends AsyncTask<Void, Void, List<MenuJoin>>{
        @Override
        protected List<MenuJoin> doInBackground(Void... voids) {
            return dao.getMenuJoinList();
        }
        @Override
        protected void onPostExecute(List<MenuJoin> menuJoins) {
            super.onPostExecute(menuJoins);
            if (menuJoins != null) {
                joinList.setValue(menuJoins);
            }
        }
    }


}
