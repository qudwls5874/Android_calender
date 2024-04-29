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
        selectList(0);
    }


    public void selectList(int categoryFg){new SelectAllAsyncTask(categoryFg).execute();}
    public class SelectAllAsyncTask extends AsyncTask<Void, Void, List<MenuJoin>>{
        private int categoryFg;
        public SelectAllAsyncTask(int categoryFg) {
            this.categoryFg = categoryFg;
        }
        @Override
        protected List<MenuJoin> doInBackground(Void... voids) {
            return dao.getMenuJoinList(categoryFg);
        }
        @Override
        protected void onPostExecute(List<MenuJoin> menuJoins) {
            super.onPostExecute(menuJoins);
            if (menuJoins != null) {
                joinList.setValue(menuJoins);
            }
        }
    }

    public void insertList(MenuList menuList, int categoryFg){new InsertAsyncTask(categoryFg).execute(menuList);}
    public class InsertAsyncTask extends AsyncTask<MenuList, Void, List<MenuJoin>>{
        private int categoryFg;
        public InsertAsyncTask(int categoryFg) {
            this.categoryFg = categoryFg;
        }
        @Override
        protected List<MenuJoin> doInBackground(MenuList... menuLists) {
            dao.insert(menuLists[0]);
            return dao.getMenuJoinList(categoryFg);
        }
        @Override
        protected void onPostExecute(List<MenuJoin> menuJoins) {
            super.onPostExecute(menuJoins);
            joinList.setValue(menuJoins);
        }
    }

    public void update(MenuList menuList, int categoryFg){new UpdateAsyncTask(categoryFg).execute(menuList);}
    private class UpdateAsyncTask extends AsyncTask<MenuList, Void, List<MenuJoin>>{
        private int categoryFg;
        public UpdateAsyncTask(int categoryFg) {
            this.categoryFg = categoryFg;
        }
        @Override
        protected List<MenuJoin> doInBackground(MenuList... menuLists) {
            dao.update(menuLists[0]);
            return dao.getMenuJoinList(categoryFg);
        }
        @Override
        protected void onPostExecute(List<MenuJoin> menuJoins) {
            super.onPostExecute(menuJoins);
            joinList.setValue(menuJoins);
        }
    }

    public void delete(List<MenuList> menuList, int categoryFg){ new DeleteAsyncTask(categoryFg).execute(menuList); }
    public class DeleteAsyncTask extends AsyncTask<List<MenuList>, Void, List<MenuJoin>>{
        private int categoryFg;
        public DeleteAsyncTask(int categoryFg) {
            this.categoryFg = categoryFg;
        }
        @Override
        protected List<MenuJoin> doInBackground(List<MenuList>... lists) {
            dao.deleteChoice(lists[0]);
            return dao.getMenuJoinList(categoryFg);
        }
        @Override
        protected void onPostExecute(List<MenuJoin> menuJoins) {
            super.onPostExecute(menuJoins);
            joinList.setValue(menuJoins);
        }
    }


}
