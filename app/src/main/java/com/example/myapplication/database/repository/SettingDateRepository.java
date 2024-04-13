package com.example.myapplication.database.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.myapplication.database.UserDatabase;
import com.example.myapplication.database.dao.SettingDateDao;
import com.example.myapplication.database.table.MoneyName;
import com.example.myapplication.database.table.SettingDate;

import java.util.List;

public class SettingDateRepository {

    public MutableLiveData<List<SettingDate>> settingDateList = new MutableLiveData<List<SettingDate>>() {};
    private SettingDateDao settingDateDao;

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
            settingDateList.setValue(settingDates);
        }
    }

    // UdateAsyncTask
    public void update(List<SettingDate> settingDate){ new settingUpdateAsyncTask().execute(settingDate);}

    private class settingUpdateAsyncTask extends AsyncTask<List<SettingDate>, Void, Void>{

        @Override
        protected Void doInBackground(List<SettingDate>... settingDates) {
            settingDateDao.updateAll(settingDates[0]);
            return null;
        }
    }

}
