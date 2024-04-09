package com.example.myapplication.database.viewmodel;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.myapplication.database.UserDatabase;
import com.example.myapplication.database.dao.MoneyNameDao;
import com.example.myapplication.database.table.MoneyName;

import java.util.List;

public class MoneyNameViewModel extends AndroidViewModel {

    private MutableLiveData<List<MoneyName>> modelMoneyNameList = new MutableLiveData<>();
    private MoneyNameDao moneyNameDao;

    public MoneyNameViewModel(@NonNull Application application) {
        super(application);
        UserDatabase db = Room.databaseBuilder(application, UserDatabase.class, "moneyname")
                .fallbackToDestructiveMigration()
                .addCallback(new RoomDatabase.Callback() {
                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                        super.onCreate(db);
                        // 여기서부터 수정하기/
                        moneyNameDao.insert(new MoneyName("정액제"));
                    }
                }).build();
        moneyNameDao = db.getMoneyName();
        select();
    }

    public LiveData<List<MoneyName>> getAllMoneyList(){return modelMoneyNameList;}

    public void select(){new SelectMoneyNameAsyncTask().execute();}

    public class SelectMoneyNameAsyncTask extends AsyncTask<Void, Void, List<MoneyName>> {
        @Override
        protected List<MoneyName> doInBackground(Void... voids) {
            return moneyNameDao.getAllMoney();
        }
        @Override
        protected void onPostExecute(List<MoneyName> MoneyNameList) {
            super.onPostExecute(MoneyNameList);
            modelMoneyNameList.setValue(MoneyNameList);
        }
    }

/*
    public void insert(MoneyName moneyName){ new InserMoneyNameAsyncTask().execute(moneyName); }

    public class InserMoneyNameAsyncTask extends AsyncTask<MoneyName, Void, Void> {
        @Override
        protected Void doInBackground(MoneyName... moneyNames) {
            moneyNameDao.insert(moneyNames[0]);
            return null;
        }
        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            select();
        }
    }
    public void update(MoneyName user) {
        new UpdateUserAsyncTask().execute(user);
    }

    private class UpdateUserAsyncTask extends AsyncTask<User, Void, Void> {
        @Override
        protected Void doInBackground(User... users) {
            moneyNameDao.updateUser(users[0]);
            return null;
        }
        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            select();
        }
    }

    public void delete(User user) {
        new DeleteUserAsyncTask().execute(user);
    }

    private class DeleteUserAsyncTask extends AsyncTask<User, Void, Void> {
        @Override
        protected Void doInBackground(User... users) {
            moneyNameDao.deleteUser(users[0]);
            return null;
        }
        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            select();
        }
    }
*/
}
