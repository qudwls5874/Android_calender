package com.example.myapplication.database.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.myapplication.database.UserDatabase;
import com.example.myapplication.database.dao.SettingDateDao;
import com.example.myapplication.database.table.SettingDate;

import java.util.List;

public class SettingDateRepository {

    private SettingDateDao settingDateDao;
    public LiveData<List<SettingDate>> settingDateList = new LiveData<List<SettingDate>>() {};

    public SettingDateRepository(Application application){
        UserDatabase db = UserDatabase.getInstance(application);
        settingDateDao = db.getSettingDao();
        select();
    }
    // SelectAsyncTask
    private void select() {new SelectAsyncTask().execute();}

    private class SelectAsyncTask extends AsyncTask<Void, Void, List<SettingDate>> {
        @Override
        protected List<SettingDate> doInBackground(Void... voids) {
            return settingDateDao.getAllSelect();
        }

        @Override
        protected void onPostExecute(List<SettingDate> settingDates) {
            super.onPostExecute(settingDates);
            settingDateList = new LiveData<List<SettingDate>>() {
                @Override
                protected void setValue(List<SettingDate> value) {
                    super.setValue(settingDates);
                }
            };
        }
    }

}
