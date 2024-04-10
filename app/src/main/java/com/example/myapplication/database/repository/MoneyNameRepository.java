package com.example.myapplication.database.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.MutableLiveData;

import com.example.myapplication.database.UserDatabase;
import com.example.myapplication.database.dao.MoneyNameDao;
import com.example.myapplication.database.table.MoneyName;

import java.util.List;

public class MoneyNameRepository {

    private MoneyNameDao moneyNameDao;
    private MutableLiveData<List<MoneyName>> allMoneyNames = new MutableLiveData<>();

    public MoneyNameRepository(Application application){
        UserDatabase database = UserDatabase.getInstance(application);
        moneyNameDao = database.getMoneyNameDao();
        select();
        // allMoneyNames.setValue(moneyNameDao.getAllMoney());
    }

    public MutableLiveData<List<MoneyName>> getAllMoneyNames(){ return allMoneyNames; }
    // public LiveData<List<MoneyName>> getAllMoneyList(){return allMoneyNames;}

    public void select(){new SelectMoneyNameAsyncTask().execute();}

    public class SelectMoneyNameAsyncTask extends AsyncTask<Void, Void, List<MoneyName>> {
        @Override
        protected List<MoneyName> doInBackground(Void... voids) {
            return moneyNameDao.getAllMoney();
        }
        @Override
        protected void onPostExecute(List<MoneyName> MoneyNameList) {
            super.onPostExecute(MoneyNameList);
            allMoneyNames.setValue(MoneyNameList);
        }
    }



    public void insert(MoneyName moneyName){
        new InsertMoneyNameAsyncTask(moneyNameDao).execute(moneyName);
    }

    private static class InsertMoneyNameAsyncTask extends AsyncTask<MoneyName, Void, Void>{
        private MoneyNameDao moneyNameDao;
        private InsertMoneyNameAsyncTask(MoneyNameDao moneyNameDao){
            this.moneyNameDao = moneyNameDao;
        }
        @Override
        protected Void doInBackground(MoneyName... moneyNames) {
            moneyNameDao.insert(moneyNames[0]);
            return null;
        }
    }




}
