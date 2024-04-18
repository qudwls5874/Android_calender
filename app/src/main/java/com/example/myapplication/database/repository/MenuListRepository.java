package com.example.myapplication.database.repository;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.myapplication.database.UserDatabase;
import com.example.myapplication.database.dao.MenuListDao;
import com.example.myapplication.database.table.MenuList;
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

    public void insertList(MenuList menuList){new InsertAsyncTask().execute(menuList);}
    public class InsertAsyncTask extends AsyncTask<MenuList, Void, List<MenuJoin>>{
        @Override
        protected List<MenuJoin> doInBackground(MenuList... menuLists) {
            dao.insert(menuLists[0]);
            return dao.getMenuJoinList();
        }
        @Override
        protected void onPostExecute(List<MenuJoin> menuJoins) {
            super.onPostExecute(menuJoins);
            joinList.setValue(menuJoins);
        }
    }

    public void delete(List<MenuList> menuList){
        for (MenuList data : menuList){
            Log.i("List 데이터2", data.getMenuName());
            Log.i("List 데이터2", ""+data.getMenuListId());
        }
        new DeleteAsyncTask().execute(menuList);
    }
    public class DeleteAsyncTask extends AsyncTask<List<MenuList>, Void, List<MenuJoin>>{
        @Override
        protected List<MenuJoin> doInBackground(List<MenuList>... lists) {
            dao.deleteChoice(lists[0]);
            return dao.getMenuJoinList();
        }
        @Override
        protected void onPostExecute(List<MenuJoin> menuJoins) {
            super.onPostExecute(menuJoins);
            joinList.setValue(menuJoins);
        }
    }


}
