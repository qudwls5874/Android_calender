package com.example.myapplication.database.repository;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.myapplication.database.UserDatabase;
import com.example.myapplication.database.dao.MenuCategoryDao;
import com.example.myapplication.database.table.MenuCategory;

import java.util.List;

public class MenuCategoryRepository {

    public MutableLiveData<List<MenuCategory>> list = new MutableLiveData<>();
    private MenuCategoryDao dao;

    public MenuCategoryRepository(Application application){
        UserDatabase database = UserDatabase.getInstance(application);
        dao = database.getMenuCategoryDao();
        selectList();
    }

    private void selectList(){new SelectAllAsyncTask().execute();}

    private class SelectAllAsyncTask extends AsyncTask<Void, Void, List<MenuCategory>>{
        @Override
        protected List<MenuCategory> doInBackground(Void... voids) {
            return dao.getAllSelect();
        }
        @Override
        protected void onPostExecute(List<MenuCategory> menuCategories) {
            super.onPostExecute(menuCategories);
            list.setValue(menuCategories);
        }
    }


}
